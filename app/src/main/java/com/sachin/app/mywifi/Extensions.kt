package com.sachin.app.mywifi

import android.webkit.WebView

fun WebView.invokeJs(js: String, resultCallback: ((result: String) -> Unit)? = null) {
    evaluateJavascript("(function() { $js })();", resultCallback)
}