package com.sachin.app.mywifi.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

@SuppressLint("SetJavaScriptEnabled")
class JsWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : WebView(context, attrs){

    init {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
    }
}