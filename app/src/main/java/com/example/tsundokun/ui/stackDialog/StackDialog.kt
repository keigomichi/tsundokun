package com.example.tsundokun.ui.stackDialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.example.tsundokun.R.drawable
import com.example.tsundokun.R.string
import com.example.tsundokun.domain.models.Category
import com.example.tsundokun.ui.confirm.component.SelectedShow
import com.example.tsundokun.ui.confirm.component.jsoup.FetchHtml
import com.example.tsundokun.ui.confirm.component.jsoup.GetTitle
import com.example.tsundokun.ui.confirm.component.jsoup.getOgpImageUrl

@Composable
fun StackDialog(
    onDismiss: () -> Unit,
    linkText: String,
    viewModel: ConfirmDialogViewModel = hiltViewModel(),
) {
    var selectedOptionText by remember { mutableStateOf("") }
    val enableClickButton = selectedOptionText != ""

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false, // widthを自由に設定する
        ),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column {
                OgpAndTitleField(linkText = linkText)

                LinkField(
                    linkText = linkText,
                    icon = Filled.Attachment,
                    modifier = Modifier.padding(16.dp, 8.dp),
                )

                SelectedShow(
                    text = selectedOptionText,
                    onTextChange = { selectedOptionText = it.label },
                    icon = Outlined.Category,
                    title = stringResource(string.category),
                    modifier = Modifier.padding(16.dp, 8.dp),
                    //FIXME: 仮のデータ。Categoryにしているのは、確認画面のカテゴリ選択に合わせているためこのよな形になっている。
                    options = listOf(
                        Category(id = "", label = "本", createdAt = Long.MAX_VALUE),
                        Category(id = "", label = "漫画", createdAt = Long.MAX_VALUE),
                        Category(id = "", label = "雑誌", createdAt = Long.MAX_VALUE),
                        Category(id = "", label = "その他", createdAt = Long.MAX_VALUE),
                    )
                )

                AddButton(
                    linkText = linkText,
                    enableClickButton = enableClickButton,
                    viewModel = viewModel,
                    onDismiss = onDismiss,
                )
            }
        }
    }
}

/*
 * つんどく追加ダイアログのリンク表示欄
 */
@Composable
private fun LinkField(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    linkText: String,
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
        Text(linkText, Modifier.fillMaxWidth())
    }
}

/*
 * つんどく追加ダイアログのOGPとタイトルの表示欄
 */
@Composable
private fun OgpAndTitleField(
    linkText: String,
) {
    val html = FetchHtml(linkText)
    val ogpImageUrl = getOgpImageUrl(html)
    val title = GetTitle(html)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        ) {
            val painter = rememberAsyncImagePainter(
                Builder(LocalContext.current).data(data = ogpImageUrl)
                    .apply(block = fun Builder.() {
                        error(drawable.loading)
                    }).build(),
            )
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
            )
        }
        Text(
            text = title ?: "",
            modifier = Modifier.padding(0.dp, 5.dp),
            textAlign = TextAlign.Center,
        )
    }
}

/*
 * つんどく追加ダイアログの追加ボタン
 */
@Composable
private fun AddButton(
    linkText: String,
    enableClickButton: Boolean,
    viewModel: ConfirmDialogViewModel,
    onDismiss: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    ) {
        Button(
            onClick = {
                viewModel.addTsundoku(linkText)
                onDismiss()
            },
            enabled = enableClickButton,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
        ) {
            Text(text = "追加する")
        }
    }
}
