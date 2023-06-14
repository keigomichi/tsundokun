package com.example.tsundokun

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tsundokun.R.string
import com.example.tsundokun.ui.NavGraphs
import com.example.tsundokun.ui.stack.StackScreen
import com.example.tsundokun.ui.theme.TsundokunTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

        remoteConfig.fetchAndActivate().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val updated = task.result
                Log.d(TAG, "Config params updated: $updated")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d(TAG, "Updated keys: " + configUpdate.updatedKeys)
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w(TAG, "Config update error with code: " + error.code, error)
            }
        })
    }

    @Composable
    fun MainScreen() {
        val remoteConfig = Firebase.remoteConfig
        val currentVersion = remoteConfig[CURRENT_VERSION_KEY].asString()
        val requireForceUpdate = remoteConfig[REQUIRE_FORCE_UPDATE_KEY].asBoolean()

        val showDialog = remember { mutableStateOf(false) }

        if (currentVersion != packageManager.getPackageInfo(
                packageName,
                0
            ).versionName
        ) {
            showDialog.value = true
        }

        TsundokunTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                DestinationsNavHost(navGraph = NavGraphs.root)
                if (showDialog.value) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f))
                    ) {
                        ShowUpdate(showDialog, requireForceUpdate)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"

        private const val CURRENT_VERSION_KEY = "current_version"
        private const val REQUIRE_FORCE_UPDATE_KEY = "require_force_update"
    }

    @Composable
    fun ShowUpdate(showDialog: MutableState<Boolean>, requireForceUpdate: Boolean = false) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text(stringResource(string.do_update))
                }
            },
            dismissButton = {
                if (!requireForceUpdate) {
                    Button(onClick = { showDialog.value = false }) {
                        Text(stringResource(string.button_close))
                    }
                }
            },
            text = { Text(stringResource(string.need_update_info)) }
        )
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
