package com.example.tsundokun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tsundokun.domain.models.Tsundoku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.util.UUID

@Entity(tableName = "tsundoku")
data class TsundokuEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "link") val link: String = "",
    @ColumnInfo(name = "is_read") val isRead: Boolean = false,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false,
    @ColumnInfo(name = "created_at") var createdAt: String = "",
    @ColumnInfo(name = "update_at") val updatedAt: String = "",
    @ColumnInfo(name = "deleted_at") val deletedAt: String = "",
) {
    companion object {
        fun fromTsundoku(tsundoku: Tsundoku) = TsundokuEntity(
            link = tsundoku.link,
        )

        fun fromLink(link: String) = TsundokuEntity(
            link = link,
        )
    }
}

/*
 * htmlからogp画像のurlを取得
 */
suspend fun getOgpImageUrl(html: String?): String? {
    var imageUrl: String? = null
    withContext(Dispatchers.IO) {
        try {
            val doc = html?.let { Jsoup.parse(it) }
            val ogImage = doc?.selectFirst("meta[property=og:image]")
            if (ogImage != null) {
                imageUrl = ogImage.attr("content")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return imageUrl
}

/*
 * htmlからfavicon画像のurlを取得
 */
suspend fun getFaviconImageUrl(html: String?): String? {
    var imageUrl: String? = null
    withContext(Dispatchers.IO) {
        try {
            val doc = html?.let { Jsoup.parse(it) }
            val ogImage = doc?.selectFirst("[href~=.*\\.(ico|png)]")
            if (ogImage != null) {
                imageUrl = ogImage.attr("href")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return imageUrl
}

/*
 * htmlを取得
 */
suspend fun fetchHtml(url: String): String? {
    var html: String? = null
    withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(url).get()
            html = doc.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return html
}

/*
 * htmlからタイトルを取得
 */

suspend fun getTitle(html: String?): String? {
    var title: String? = null

    withContext(Dispatchers.IO) {
        val doc = html?.let { Jsoup.parse(it) }
        try {
            val doc = html?.let { Jsoup.parse(it) }
            if (doc != null) {
                title = doc.title()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return title
}

/*
 * TsundokuEntityをTsundokuに変換
 */
suspend fun List<TsundokuEntity>.toTsundoku(): List<Tsundoku> = map {
    Tsundoku(
        id = it.id,
        title = getTitle(html = fetchHtml(url = it.link)),
        ogpImageUrl = getOgpImageUrl(html = fetchHtml(url = it.link)),
        faviconImageUrl = getFaviconImageUrl(html = fetchHtml(url = it.link)),
        link = it.link,
        isFavorite = it.isFavorite,
        createdAt = it.createdAt,
        isRead = it.isRead,
        updatedAt = it.updatedAt,
        deletedAt = it.deletedAt,
    )
}

/*
 * TsundokuをTsundokuEntityに変換
 */
suspend fun Tsundoku.toEntity(): TsundokuEntity {
    return TsundokuEntity(
        id = id,
        link = link,
        isRead = isRead,
        isFavorite = isFavorite,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}

