package com.example.tsundokun.ui.confirm.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tsundokun.R.string
import com.example.tsundokun.domain.models.Category
import com.example.tsundokun.ui.confirm.component.jsoup.FetchHtml
import com.example.tsundokun.ui.confirm.component.jsoup.GetTitle

/*
 * つんどく追加画面の入力欄
 */
@Composable
fun AllInputFields(
    modifier: Modifier = Modifier,
    onFieldsValidated: (Boolean) -> Unit,
    onTextChange: (Category) -> Unit,
    link: String,
    options: List<Category>,
) {
    var selectedOptionText by remember { mutableStateOf("") }

    val html = FetchHtml(link)
    val title = GetTitle(html)

    val fieldsAreValid = selectedOptionText.isNotBlank()
    onFieldsValidated(fieldsAreValid)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShowOgp(
            modifier = Modifier.padding(0.dp, 20.dp),
            image = link,
        )
        if (title != null) {
            Text(
                text = title,
                modifier = Modifier.padding(0.dp, 5.dp),
                textAlign = TextAlign.Center,
            )
        } else {
            Text(text = "", modifier = Modifier.padding(0.dp, 5.dp))
        }
        ShowText(
            icon = Filled.Attachment,
            title = link,
        )
        SelectedShow(
            text = selectedOptionText,
            onTextChange = {
                onTextChange(it)
                selectedOptionText = it.label
            },
            icon = Outlined.Category,
            title = stringResource(string.category),
            modifier = Modifier.padding(0.dp, 5.dp),
            options = options,
        )
    }
}
