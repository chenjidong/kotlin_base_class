package com.cjd.base.utils

import com.google.gson.Gson

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/3
 * description
 */
object GsonUtils {

    @JvmStatic
    private var gson: Gson? = null

    @JvmStatic
    fun instance(): Gson {
        if (gson == null)
            gson = Gson()
        return gson as Gson
    }
}