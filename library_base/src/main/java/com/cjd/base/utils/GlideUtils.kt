package com.cjd.base.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.Headers
import com.cjd.base.preferences.PreferencesHelper

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/16
 * description
 */
object GlideUtils {

    fun loadLicenseUrl(context: Context, url: String, iv: ImageView) {
        val headers = Headers {
            val auth = PreferencesHelper.getString(context, OkHttpUtils.AUTH, "")
            mutableMapOf(OkHttpUtils.AUTH to "bearer $auth")
        }
        val glideUrl = GlideUrl(url, headers)
        Glide.with(context).load(glideUrl).into(iv)
    }
}