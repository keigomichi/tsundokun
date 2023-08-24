package com.example.tsundokun.ui.home.component.webPageList

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.ui.home.component.webPageList.webPageCard.WebPageCard
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun TsundokuListScreen(selectedCategoryTsundokuList: List<TsundokuEntity>,navigator: DestinationsNavigator){
    LazyColumn(){
        itemsIndexed(selectedCategoryTsundokuList) { _, tsundoku ->
//            WebPageCard(
//                webpage = tsundoku.link,
//                navigator = navigator
//            )
            Divider(color = Color.Gray) // 区切り線
        }
    }

}
