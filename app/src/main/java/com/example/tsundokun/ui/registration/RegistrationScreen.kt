package com.example.tsundokun.ui.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tsundokun.ui.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun RegistrationScreen(navigator: DestinationsNavigator) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        ) {
            Text(text = "つんどくん", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "メールアドレス") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "パスワード") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* 新規登録処理を実行 */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
            ) {
                Text(text = "新規登録")
            }
            Text(
                text = "ログインはこちら",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable {
                    navigator.navigate(
                        LoginScreenDestination(),
                    )
                },
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "または", modifier = Modifier.padding(vertical = 10.dp))
            Text(text = "googleアカウントで登録", modifier = Modifier.clickable { /*TODO*/ })
        }
    }
}
