package com.example.tsundokun.ui.stack

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tsundokun.R.string
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Destination
@Composable
fun StackScreen(navigator: DestinationsNavigator) {
    var linkText by remember { mutableStateOf("") }
    var fieldsAreValid by remember(linkText) {
        mutableStateOf(
            linkText.isNotBlank(),
        )
    }
    var showError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = {
                StackAppBar(
                    url = linkText,
                    fieldsAreValid = fieldsAreValid,
                    navigator,
                    showDialog = { showDialog = it },
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                Fields(
                    linkText = linkText,
                    onLinkTextChange = {
                        linkText = it
                        fieldsAreValid = it.isNotBlank()
                        showError = false
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp),
                )
            }
        }
        if (showDialog) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
            ) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    text = { Text(text = stringResource(string.cant_add_url)) },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text(text = stringResource(string.button_close))
                        }
                    },
                    confirmButton = {
                    },
                )
            }
        }
    }
}

/*
 * つんどく追加画面の入力欄
 */
@Composable
fun Fields(
    linkText: String,
    onLinkTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceEvenly) {
        Text(
            text = (stringResource(string.add_url_text)),
            modifier = Modifier.padding(20.dp, 10.dp),
        )
        InputField(
            text = linkText,
            onTextChange = onLinkTextChange,
            icon = Filled.Attachment,
            title = stringResource(string.link),
            modifier = Modifier.padding(20.dp, 0.dp),
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
                    }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear text")
                    }
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
        )
    }
}
