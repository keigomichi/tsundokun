package com.example.tsundokun.ui.confirm.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.tsundokun.R.drawable
import com.example.tsundokun.ui.confirm.component.jsoup.FetchHtml
import com.example.tsundokun.ui.confirm.component.jsoup.GetOgpImageUrl

/*
 * OGP表示
 */
@Composable
fun ShowOgp(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    image: String,
) {
    val html = FetchHtml(image)
    val ogpImageUrl = GetOgpImageUrl(html)

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
            val painter = rememberImagePainter(data = ogpImageUrl, builder = {
                error(drawable.loading)
            })
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
            )
        }
    }
}
