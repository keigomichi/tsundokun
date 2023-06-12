package com.example.tsundokun.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WebPage(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int,
)
