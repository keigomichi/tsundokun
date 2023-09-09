package com.example.tsundokun.ui.stack

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.tsundokun.R.string
import com.example.tsundokun.ui.confirm.component.data.ConfirmScreenNavArgs
import com.example.tsundokun.ui.destinations.ConfirmScreenDestination
import com.example.tsundokun.ui.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/*
 * つんどく追加画面のアプリバー
 */
@RequiresApi(VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StackAppBar(
    url: String,
    fieldsAreValid: Boolean,
    navigator: DestinationsNavigator,
    showDialog: (Boolean) -> Unit,
) {
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
            if (url.startsWith("http")) {
                IconButton(onClick = {
                    navigator.navigate(
                        ConfirmScreenDestination(
                            ConfirmScreenNavArgs(
                                link = url,
                                categoryId = "",
                            ),
                        ),
                    )
                }) {
                    Icon(
                        imageVector = Outlined.Send,
                        contentDescription = stringResource(string.do_add),
                    )
                }
            } else {
                IconButton(onClick = { showDialog(true) }) {
                    Icon(
                        imageVector = Outlined.Send,
                        contentDescription = stringResource(string.do_add),
                    )
                }
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
