package com.example.tsundokun

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tsundokun.ui.NavGraphs
import com.example.tsundokun.ui.stack.StackScreen
import com.example.tsundokun.ui.theme.TsundokunTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TsundokunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (Intent.ACTION_SEND.equals(intent?.action) && intent?.type != null) {
            if ("text/plain".equals(intent?.type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    private fun handleSendText(intent: Intent?) {
        val sharedText = intent!!.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            setContent {
                TsundokunTheme {
                    // Launch the desired Composable with the shared URL
                    StackScreen(url = Uri.parse(sharedText))
                }
            }
        }
    }
}
