package com.example.tsundokun.ui.stack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tsundokun.R.string
import com.example.tsundokun.ui.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun StackScreen(navigator: DestinationsNavigator) {
    var fieldsAreValid by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = {
                StackAppBar(
                    fieldsAreValid = fieldsAreValid,
                    navigator,
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                AllInputFields(
                    onFieldsValidated = { fieldsAreValid = it },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp),
                )
            }
        }
    }
}

/*
 * つんどく追加画面のアプリバー
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StackAppBar(fieldsAreValid: Boolean, navigator: DestinationsNavigator) {
    TopAppBar(title = {
        Text(
            text = stringResource(string.add),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }, navigationIcon = {
        IconButton(onClick = { navigator.navigate(HomeScreenDestination()) }) {
            Icon(
                imageVector = Outlined.Close,
                contentDescription = stringResource(string.button_close),
            )
        }
    }, actions = {
        if (fieldsAreValid) {
            IconButton(onClick = { /* つんどくを追加する */ }) {
                Icon(
                    imageVector = Outlined.Send,
                    contentDescription = stringResource(string.do_add),
                )
            }
        } else {
            IconButton(onClick = { /* 追加出来ない状態 */ }) {
                Icon(
                    imageVector = Outlined.Send,
                    contentDescription = stringResource(string.do_add),
                    tint = Color.Gray,
                )
            }
        }
    })
}

/*
 * タイトル表示
 */
@Composable
private fun Title(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    title: String = "",
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end = 8.dp),
        )
        Text(
            text = title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
    }
}

/*
 * つんどく追加画面の入力欄
 */
@Composable
fun AllInputFields(modifier: Modifier = Modifier, onFieldsValidated: (Boolean) -> Unit) {
    var linkText by remember { mutableStateOf("") }
    var selectedOptionText by remember { mutableStateOf("") }
    var fetchLinkText by remember { mutableStateOf("") }

    val html = fetchHtml(fetchLinkText)
    val title = getTitle(html)

    val fieldsAreValid =
        linkText.isNotBlank() && selectedOptionText.isNotBlank()
    onFieldsValidated(fieldsAreValid)

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceEvenly) {
        Title(icon = Outlined.Title, title = title ?: "")

        InputField(
            text = linkText,
            onTextChange = { linkText = it },
            onEnter = { fetchLinkText = linkText },
            icon = Filled.Attachment,
            title = stringResource(string.link),
            modifier = Modifier.padding(0.dp, 5.dp),
        )
        SelectedShow(
            text = selectedOptionText,
            onTextChange = { selectedOptionText = it },
            icon = Outlined.Category,
            title = stringResource(string.category),
            modifier = Modifier.padding(0.dp, 5.dp),
        )
        ShowOgp(
            modifier = Modifier.padding(0.dp, 5.dp),
            image = fetchLinkText,
        )
    }
}

/*
 * つんどく追加画面の入力欄のテンプレート
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun InputField(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    title: String = "",
    text: String,
    onTextChange: (String) -> Unit,
    onEnter: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end = 8.dp),
        )
        OutlinedTextField(
            text,
            { onTextChange(it) },
            Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            singleLine = true,
            maxLines = 1,
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = {
                        onTextChange("")
                        onEnter()
                    }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear text")
                    }
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onEnter()
            }),
        )
    }
}

/*
 * カテゴリ選択
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectedField(
    modifier: Modifier = Modifier,
    title: String = "",
    text: String,
    onTextChange: (String) -> Unit,
) {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = text,
            onValueChange = { /* do nothing, text is read only */ },
            label = { Text(title) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            readOnly = true,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onTextChange(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

/*
 * アイコンとカテゴリ選択
 */
@Composable
private fun SelectedShow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    title: String = "",
    text: String,
    onTextChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end = 8.dp),
        )
        SelectedField(
            modifier = Modifier.weight(1f),
            title = title,
            text = text,
            onTextChange = onTextChange,
        )
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
 * OGP表示
 */
@Composable
private fun ShowOgp(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    image: String,
) {
    val html = fetchHtml(image)
    val ogpImageUrl = getOgpImageUrl(html)
    Row(
        modifier = modifier.padding(0.dp, 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Outlined.Image,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end = 8.dp),
        )
        Image(
            painter = rememberAsyncImagePainter(model = ogpImageUrl),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(150.dp, 100.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
        )
    }
}
