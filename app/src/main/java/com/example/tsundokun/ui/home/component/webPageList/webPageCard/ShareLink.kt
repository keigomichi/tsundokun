package com.example.tsundokun.ui.home.component.webPageList.webPageCard

import android.content.Context
import android.content.Intent
import com.example.tsundokun.R.string

fun ShareLink(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, link)
    val chooserIntent = Intent.createChooser(intent, context.getString(string.share_link))
    context.startActivity(chooserIntent)
}
