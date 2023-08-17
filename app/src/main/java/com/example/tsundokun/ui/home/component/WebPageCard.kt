package com.example.tsundokun.ui.home.component

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tsundokun.R.string
import com.example.tsundokun.ui.destinations.OpenWebViewDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/*
 * リストの各要素であるカード
 */
@Composable
fun WebPageCard(webpage: WebPage, modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    val context = LocalContext.current
    Card(
        modifier = modifier.clickable { navigator.navigate(OpenWebViewDestination(url = webpage.link!!)) },
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = webpage.title ?: "",
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(5f),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Image(
                    painter = rememberAsyncImagePainter(webpage.ogpImageUrl),
                    contentDescription = webpage.title,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(96.dp)
                        .weight(3f),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .weight(1f),
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = webpage.faviconImageUrl),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(50)),
                    )
                }
                Text(
                    text = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = modifier.weight(2f))
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Filled.FavoriteBorder,
                        contentDescription = stringResource(string.button_favorite_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                    )
                }
                IconButton(onClick = { webpage.link?.let { ShareLink(context, it) } }) {
                    Icon(
                        imageVector = Filled.Share,
                        contentDescription = stringResource(string.button_share_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                    )
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Filled.MoreVert,
                        contentDescription = stringResource(string.button_morevert_description),
                        modifier = modifier
                            .weight(1f)
                            .width(20.dp),
                    )
                }
            }
        }
    }
}

private fun ShareLink(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, link)
    val chooserIntent = Intent.createChooser(intent, context.getString(string.share_link))
    context.startActivity(chooserIntent)
}
