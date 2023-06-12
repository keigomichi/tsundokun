package com.example.tsundokun.data

import com.example.tsundokun.R
import com.example.tsundokun.model.WebPage

class Datasource() {
    fun loadWebPage(): List<WebPage> {
        return listOf<WebPage>(
            WebPage(R.string.sample_web_page_title, R.drawable.sample_image),
            WebPage(R.string.sample_web_page_title, R.drawable.sample_image),
            WebPage(R.string.sample_web_page_title, R.drawable.sample_image),
            WebPage(R.string.sample_web_page_title, R.drawable.sample_image),
            WebPage(R.string.sample_web_page_title, R.drawable.sample_image),
            WebPage(R.string.sample_web_page_title, R.drawable.sample_image),
            WebPage(R.string.sample_web_page_title, R.drawable.sample_image),
        )
    }
}
