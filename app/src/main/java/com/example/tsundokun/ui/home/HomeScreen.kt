package com.example.tsundokun.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.tsundokun.R
import com.example.tsundokun.R.string
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.ui.destinations.OpenWebViewDestination
import com.example.tsundokun.ui.destinations.SettingScreenDestination
import com.example.tsundokun.ui.destinations.StackScreenDestination
import com.example.tsundokun.ui.home.component.AddTabTitleDialog
import com.example.tsundokun.ui.home.component.CardDropdown
import com.example.tsundokun.ui.home.component.TsundokunReport
import com.example.tsundokun.ui.theme.Pink80
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(VERSION_CODES.O)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator, viewModel: HomeViewModel = hiltViewModel()) {
    val tsundokuUiState by viewModel.uiState.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = { TopAppBar(navigator) },
            floatingActionButton = { AddFab(navigator) },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                TsundokunReport(Modifier, recentTsundokuData(tsundokuUiState))
                WebPageListScreen(tsundokuUiState.tsundoku, navigator)
            }
        }
    }
}

/*
* 今週の積読した数の算出
*/
@RequiresApi(VERSION_CODES.O)
fun recentTsundokuData(tsundokuUiState: TsundokuUiState): Int {
    val now = LocalDateTime.now()
    val oneWeekAgo = now.minus(1, ChronoUnit.WEEKS)
    val recentTsundokuData = tsundokuUiState.tsundoku.filter { tsundoku ->
        val date = LocalDateTime.parse(tsundoku.createdAt)
        date.isAfter(oneWeekAgo) && date.isBefore(now)
    }
    return recentTsundokuData.size
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
        modifier = Modifier.wrapContentSize(Alignment.TopStart),
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
 * Webページのリスト
 * タブで表示を切り替えられる
 */

@Composable
fun WebPageListScreen(tsundokuEntityList: List<TsundokuEntity>, navigator: DestinationsNavigator) {
    var tabName by remember {
        mutableStateOf(
            mutableListOf("すべて", "お気に入り"),
        )
    }

    var tabSelected by rememberSaveable { mutableStateOf(Screen.ALL) }

    val tsundokuItem = tsundokuEntityList.map {
        WebPage(
            getTitle(html = fetchHtml(url = it.link)),
            getOgpImageUrl(html = fetchHtml(url = it.link)),
            getFaviconImageUrl(html = fetchHtml(url = it.link)),
            it.link,
            it.isFavorite,
            it.id,
        )
    }

    val showDialog = remember { mutableStateOf(false) }
    Column {
        TabRow(
            selectedTabIndex = tabSelected.ordinal,
        ) {
            tabName.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = tabName[index].toString()) },
                    selected = tabSelected.ordinal == index,
                    onClick = { tabSelected = Screen.values()[index] },
                )
            }
            Tab(
                text = { Text("+") },
                selected = false,
                onClick = { showDialog.value = true },
            )
        }
        val allTsundokus = tsundokuItem
        var favoriteTsundoku = allTsundokus.filter { it.isFavorite }

        when (tabSelected) {
            Screen.ALL -> WebPageList(webPageList = allTsundokus, navigator = navigator)
            Screen.FAVORITE -> WebPageList(webPageList = favoriteTsundoku, navigator = navigator)
        }
    }
    if (showDialog.value) AddTabTitleDialog(setShowDialog = { showDialog.value = it }, tabList = tabName)
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
    val link: String?,
    val isFavorite: Boolean,
    val id: String,
)

/*
 * Webページのリスト(Lazy Column)の作成
 */
@Composable
fun WebPageList(webPageList: List<WebPage>, modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(webPageList) { _, webPage ->
            WebPageCard(
                webpage = webPage,
                modifier = Modifier.padding(8.dp),
                navigator = navigator,
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
fun WebPageCard(webpage: WebPage, modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    var favoriteIconColor: Color
    favoriteIconColor = if (webpage.isFavorite) { Pink80 } else { Color.DarkGray }
    val expandedState = remember { mutableStateOf(false) }
    Card(
        modifier = modifier.clickable { navigator.navigate(OpenWebViewDestination(url = webpage.link!!)) },
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
                    text = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = modifier.weight(2f))
                IconButton(onClick = {
                    viewModel.updateFavorite(webpage.id, !webpage.isFavorite)
                }) {
                    favoriteIconColor =
                        if (webpage.isFavorite) { Pink80 } else { Color.DarkGray }
                    Icon(
                        imageVector = Filled.FavoriteBorder,
                        contentDescription = stringResource(R.string.button_favorite_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                        tint = favoriteIconColor,
                    )
                }
                IconButton(onClick = { webpage.link?.let { ShareLink(context, it) } }) {
                    Icon(
                        imageVector = Filled.Share,
                        contentDescription = stringResource(R.string.button_share_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                    )
                }
                IconButton(onClick = { expandedState.value = !expandedState.value }) {
                    Icon(
                        imageVector = Filled.MoreVert,
                        contentDescription = stringResource(R.string.button_morevert_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                    )
                    if (expandedState.value) {
                        CardDropdown(expandedState, viewModel, webpage.id)
                    }
                }
            }
        }
    }
}

private fun ShareLink(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, link)
    val chooserIntent = Intent.createChooser(intent, context.getString(string.share_link))
    context.startActivity(chooserIntent)
}

/*
 * FAB(追加ボタン)
 */
@Composable
@Destination
fun AddFab(navigator: DestinationsNavigator) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(R.string.fab_add)) },
        icon = { Icon(Filled.Add, contentDescription = stringResource(R.string.fab_add)) },
        onClick = { navigator.navigate(StackScreenDestination()) },
    )
}

@Destination
@Composable
fun OpenWebView(url: String) {
    AndroidView(factory = { WebView(it) }) { webView ->
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
    }
}
