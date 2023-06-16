package com.example.tsundokun.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tsundokun.R
import com.example.tsundokun.ui.destinations.SettingScreenDestination
import com.example.tsundokun.ui.theme.TsundokunTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = { TopAppBar(navigator) },
            floatingActionButton = { AddFab() },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                TsundokunReport()
                WebPageListScreen()
            }
        }
    }
}

/*
 * ホーム画面のアプリバー
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navigator: DestinationsNavigator) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = { /* 検索する */ }) {
                Icon(
                    imageVector = Filled.Search,
                    contentDescription = stringResource(R.string.button_search_description),
                )
            }
        },
        actions = {
            Dropdown(navigator)
        },
    )
}

/*
 * アプリバー右上のメニューバーを押したときに表示させる
 * ドロップダウンリスト
 */
@Composable
fun Dropdown(navigator: DestinationsNavigator) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart),
    ) {
        IconButton(
            onClick = {
                expanded = true
            },
        ) {
            Icon(
                imageVector = Filled.MoreVert,
                contentDescription = stringResource(R.string.button_morevert_description),
            )
        }
        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                // タップされた時の背景を円にする
                .clip(RoundedCornerShape(16.dp)),
            expanded = expanded,
            // メニューの外がタップされた時に閉じる
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.dropdown_menuitem_setting)) },
                onClick = { navigator.navigate(SettingScreenDestination()) },
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.dropdown_memuitem_export)) },
                onClick = { /*TODO*/ },
            )
        }
    }
}

/*
 * アプリバーのプレビュー
 */
@Preview
@Composable
private fun TopAppBarPreview() {
//    TopAppBar()
}

/*
 * つんどくんのレポートを表示するカード
 * 閉じるボタンを押したら非表示になる
 */
@Composable
fun TsundokunReport(modifier: Modifier = Modifier) {
    val visible = remember { mutableStateOf(true) }
    if (visible.value) {
        Card(
            modifier = modifier
                .padding(16.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.tsundokun),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                )
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.tsundokun_report, 10),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = modifier.weight(10f),
                        )
                        IconButton(onClick = { visible.value = false }) {
                            Icon(
                                imageVector = Filled.Close,
                                contentDescription = stringResource(R.string.button_close),
                                Modifier
                                    .width(16.dp)
                                    .weight(1f),
                            )
                        }
                    }
                    Text(
                        text = stringResource(id = R.string.tsundokun_report_detail),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = modifier.padding(bottom = 8.dp),
                    )
                }
            }
        }
    }
}

/*
 * つんどくんのレポートを表示するカードのプレビュー
 */
@Preview
@Composable
private fun TsundokunReportPreview() {
    TsundokunReport()
}

/*
 * Webページのリスト
 * タブで表示を切り替えられる
 */
@Composable
fun WebPageListScreen() {
    var tabName = mutableMapOf("ALL" to "すべて", "FAVORITE" to "お気に入り")
    var tabSelected by rememberSaveable { mutableStateOf(Screen.ALL) }
    Column {
        TabRow(
            selectedTabIndex = tabSelected.ordinal,
        ) {
            Screen.values().map { it.name }.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = tabName[title].toString()) },
                    selected = tabSelected.ordinal == index,
                    onClick = { tabSelected = Screen.values()[index] },
                )
            }
        }

        /* 以下は一時的に表示するダミーの情報 */
        var allTsundokus = listOf<WebPage>(
            WebPage(
                getTitle(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
                getOgpImageUrl(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
                getFaviconImageUrl(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
                getOgpImageUrl(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
                getFaviconImageUrl(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
                getOgpImageUrl(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
                getFaviconImageUrl(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
                getOgpImageUrl(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
                getFaviconImageUrl(html = fetchHtml(url = "https://qiita.com/xrxoxcxox/items/912420a0afda4f39cd36")),
            ),
        )
        var favoriteTsundoku = listOf<WebPage>(
            WebPage(
                getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
            ),
        )

        when (tabSelected) {
            Screen.ALL -> WebPageList(webPageList = allTsundokus)
            Screen.FAVORITE -> WebPageList(webPageList = favoriteTsundoku)
        }
    }
}

/*
 * タブの種類
 */
enum class Screen {
    ALL, FAVORITE
}

/*
 * Webページの情報のデータクラス
 */
data class WebPage(
    val title: String?,
    val ogpImageUrl: String?,
    val faviconImageUrl: String?,
)

/*
 * Webページのリスト(Lazy Column)の作成
 */
@Composable
fun WebPageList(webPageList: List<WebPage>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(webPageList) { webPage ->
            WebPageCard(
                webpage = webPage,
                modifier = Modifier.padding(8.dp),
            )
            Divider(color = Color.Gray) // 区切り線
        }
    }
}

/*
 * htmlを取得
 */
@Composable
fun fetchHtml(url: String): String? {
    var html by remember { mutableStateOf<String?>(null) }
    var fetchError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = url) {
        withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(url).get()
                html = doc.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                fetchError = true
            }
        }
    }
    return html
}

/*
 * htmlからogp画像のurlを取得
 */
@Composable
fun getOgpImageUrl(html: String?): String? {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = html) {
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
    }
    return imageUrl
}

/*
 * htmlからfavicon画像のurlを取得
 */
@Composable
fun getFaviconImageUrl(html: String?): String? {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = html) {
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
    }
    return imageUrl
}

/*
 * htmlからタイトルを取得
 */
@Composable
fun getTitle(html: String?): String? {
    var title by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = html) {
        withContext(Dispatchers.IO) {
            try {
                val doc = html?.let { Jsoup.parse(it) }
                if (doc != null) {
                    title = doc.title()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    return title
}

/*
 * リストの各要素であるカード
 */
@Composable
fun WebPageCard(webpage: WebPage, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = webpage.title ?: "",
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(5f),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Image(
                    painter = rememberAsyncImagePainter(webpage.ogpImageUrl),
                    contentDescription = webpage.title,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(96.dp)
                        .weight(3f),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .weight(1f),
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = webpage.faviconImageUrl),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(50)),
                    )
                }
                Text(
                    text = LocalContext.current.getString(R.string.sample_web_page_type),
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = modifier.weight(2f))
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Filled.FavoriteBorder,
                        contentDescription = stringResource(R.string.button_favorite_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                    )
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Filled.Share,
                        contentDescription = stringResource(R.string.button_share_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                    )
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Filled.MoreVert,
                        contentDescription = stringResource(R.string.button_morevert_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                    )
                }
            }
        }
    }
}

/*
 * リストの各要素であるカードのプレビュー
 */
@Preview
@Composable
private fun WebPageCardPreview() {
    WebPageCard(
        WebPage(
            getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
            getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
            getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/"))
        )
    )
}

/*
 * リスト全体のプレビュー
 */
@Preview(showBackground = true)
@Composable
fun WebPagePreview() {
    TsundokunTheme {
        WebPageListScreen()
    }
}

/*
 * FAB(追加ボタン)
 */
@Composable
fun AddFab() {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(R.string.fab_add)) },
        icon = { Icon(Filled.Add, contentDescription = stringResource(R.string.fab_add)) },
        onClick = { /*TODO*/ },
    )
}

/*
 * FAB(追加ボタン)のプレビュー
 */
@Preview
@Composable
private fun AddFabPreview() {
    TsundokunTheme() {
        AddFab()
    }
}
