package com.cjd.base.preferences

import android.content.Context
import com.cjd.base.utils.GsonUtils

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/3
 * description sharePreferences 工具类
 */
object PreferencesHelper {
    private const val preferencesName = "json_data"

    @JvmStatic
    fun storeData(context: Context, key: String, value: Any?) {
        val sp = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        when (value) {
            is String -> sp.edit().putString(key, value.toString()).apply()
            is Int -> sp.edit().putInt(key, value).apply()
            is Boolean -> sp.edit().putBoolean(key, value).apply()
            is Long -> sp.edit().putLong(key, value).apply()
            is Float -> sp.edit().putFloat(key, value).apply()
        }
    }

    @JvmStatic
    fun storeJsonData(context: Context, key: String, value: Any?) {
        val sp = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        sp.edit().putString(key, GsonUtils.instance().toJson(value)).apply()
    }

    @JvmStatic
    fun <T> extractData(context: Context, key: String, clazz: Class<T>): T? {
        val value = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getString(key, null)
        value?.let { return GsonUtils.instance().fromJson(it, clazz) }
        return null
    }

    @JvmStatic
    fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
        return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getBoolean(key, defaultValue)
    }

    @JvmStatic
    fun getString(context: Context, key: String, defaultValue: String): String? {
        return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getString(key, defaultValue)
    }

    @JvmStatic
    fun getInt(context: Context, key: String, defaultValue: Int): Int {
        return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getInt(key, defaultValue)
    }

    @JvmStatic
    fun getLong(context: Context, key: String, defaultValue: Long): Long {
        return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getLong(key, defaultValue)
    }

    @JvmStatic
    fun getFloat(context: Context, key: String, defaultValue: Float): Float {
        return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getFloat(key, defaultValue)
    }

    @JvmStatic
    fun clearAll(context: Context) {
        context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).edit().clear().apply()
    }

}