package com.example.tsundokun.ui.confirm

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tsundokun.R.string
import com.example.tsundokun.ui.confirm.component.AllInputFields
import com.example.tsundokun.ui.confirm.component.data.ConfirmScreenNavArgs
import com.example.tsundokun.ui.confirm.component.jsoup.FetchHtml
import com.example.tsundokun.ui.confirm.component.jsoup.GetTitle
import com.example.tsundokun.ui.confirm.component.jsoup.getOgpImageUrl
import com.example.tsundokun.ui.confirm.component.stackappbar.StackAppBar
import com.example.tsundokun.ui.destinations.HomeScreenDestination
import com.example.tsundokun.ui.home.HomeViewModel.TsundokuUiState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Destination(navArgsDelegate = ConfirmScreenNavArgs::class)
@Composable
fun ConfirmScreen(
    navigator: DestinationsNavigator,
    viewModel: ConfirmViewModel = hiltViewModel(),
) {
    var fieldsAreValid by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isLoading by remember { mutableStateOf(true) }
    val composableScope = rememberCoroutineScope()

    val html = FetchHtml(viewModel.navArgs.link)
    val ogpImageUrl = getOgpImageUrl(html)

    LaunchedEffect(ogpImageUrl) {
        if (ogpImageUrl != null) {
            isLoading = false
        }
    }

    val title = GetTitle(html)
    var showTitleErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(title) {
        if (title.isNullOrEmpty()) {
            delay(10000)
            showTitleErrorDialog = true
        }
    }

    if (showTitleErrorDialog) {
        AlertDialog(
            onDismissRequest = { },
            text = { Text(stringResource(string.loading_error)) },
            dismissButton = {
                Button(
                    onClick = { navigator.navigate(HomeScreenDestination()) },
                ) {
                    Text(stringResource(string.back_home))
                }
            },
            confirmButton = { },
        )
    }

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
                    fieldsAreValid = fieldsAreValid,
                    navigator,
                    addTsundoku = {
                        composableScope.launch {
                            viewModel.addTsundoku()
                        }
                    },
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
                    link = viewModel.navArgs.link,
                )
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = Color(0xFF5DAAC9))
                    }
                }
            }
        }
    }
}

/*
 * カテゴリ選択
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedField(
    modifier: Modifier = Modifier,
    title: String = "",
    text: String,
    onTextChange: (String) -> Unit,
) {
    val uiState = TsundokuUiState()
    val options = uiState.category.map { it.label }
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
