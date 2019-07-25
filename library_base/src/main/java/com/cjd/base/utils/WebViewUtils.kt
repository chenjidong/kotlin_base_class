package com.cjd.base.utils

import android.view.ViewGroup
import android.webkit.WebView

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/23
 * description
 */
object WebViewUtils {

    fun releaseWebView(webView: WebView?): Boolean {
        try {
            webView?.let {
                val viewGroup: ViewGroup? = it.parent as ViewGroup?
                viewGroup?.removeView(webView)

                it.stopLoading()
                it.settings.javaScriptEnabled = false
                it.clearHistory()
                it.clearView()
                it.removeAllViews()
                it.destroy()
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }
}