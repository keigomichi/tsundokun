//package com.example.tsundokun.ui.home.component.webPageList.webPageCard
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons.Filled
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material.icons.filled.MoreVert
//import androidx.compose.material.icons.filled.Share
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import coil.compose.rememberAsyncImagePainter
//import com.example.tsundokun.R
//import com.example.tsundokun.ui.destinations.OpenWebViewDestination
//import com.example.tsundokun.ui.home.HomeViewModel
//import com.example.tsundokun.ui.theme.Pink80
//import com.ramcosta.composedestinations.navigation.DestinationsNavigator
//
///*
// * リストの各要素であるカード
// */
//@Composable
//fun WebPageCard(webpage: WebPage, modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
//    val viewModel: HomeViewModel = hiltViewModel()
//    val context = LocalContext.current
//    var favoriteIconColor: Color
//    favoriteIconColor = if (webpage.isFavorite) { Pink80 } else { Color.DarkGray }
//    val expandedState = remember { mutableStateOf(false) }
//    Card(
//        modifier = modifier.clickable { navigator.navigate(OpenWebViewDestination(url = webpage.link!!)) },
//        shape = RoundedCornerShape(0.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.background,
//        ),
//    ) {
//        Column {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = webpage.title ?: "",
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .weight(5f),
//                    style = MaterialTheme.typography.bodyLarge,
//                )
//                Image(
//                    painter = rememberAsyncImagePainter(webpage.ogpImageUrl),
//                    contentDescription = webpage.title,
//                    modifier = Modifier
//                        .padding(horizontal = 8.dp)
//                        .height(96.dp)
//                        .weight(3f),
//                )
//            }
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(24.dp)
//                        .weight(1f),
//                ) {
//                    Image(
//                        painter = rememberAsyncImagePainter(model = webpage.faviconImageUrl),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .clip(RoundedCornerShape(50)),
//                    )
//                }
//                Text(
//                    text = "",
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .weight(3f),
//                    style = MaterialTheme.typography.bodyMedium,
//                )
//                Spacer(modifier = modifier.weight(2f))
//                IconButton(onClick = {
////                    viewModel.updateFavorite(webpage.id, !webpage.isFavorite)
//                }) {
//                    favoriteIconColor =
//                        if (webpage.isFavorite) { Pink80 } else { Color.DarkGray }
//                    Icon(
//                        imageVector = Filled.FavoriteBorder,
//                        contentDescription = stringResource(R.string.button_favorite_description),
//                        modifier = modifier
//                            .weight(1f)
//                            .width(20.dp),
//                        tint = favoriteIconColor,
//                    )
//                }
//                IconButton(onClick = {
////                    webpage.link?.let { ShareLink(context = context, link = it) }
//                }) {
//                    Icon(
//                        imageVector = Filled.Share,
//                        contentDescription = stringResource(R.string.button_share_description),
//                        modifier = modifier
//                            .weight(1f)
//                            .width(20.dp),
//                    )
//                }
//                IconButton(onClick = { expandedState.value = !expandedState.value }) {
//                    Icon(
//                        imageVector = Filled.MoreVert,
//                        contentDescription = stringResource(R.string.button_morevert_description),
//                        modifier = modifier
//                            .weight(1f)
//                            .width(20.dp),
//                    )
//                    if (expandedState.value) {
////                        CardDropdown(expandedState, viewModel, webpage.id)
//                    }
//                }
//            }
//        }
//    }
//}
