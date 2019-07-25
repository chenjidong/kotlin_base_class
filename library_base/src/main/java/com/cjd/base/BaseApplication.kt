package com.cjd.base

import android.content.Context
import android.content.pm.PackageManager
import android.support.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import java.nio.charset.Charset


/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description
 */
open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        getVersionInfo()
        initLeakCanary()
    }

    private fun getVersionInfo() {

        val pm = packageManager
        try {
            val packageInfo = pm.getPackageInfo(packageName, 0)
            BaseGlobalVar.versionCode = packageInfo.versionCode
            BaseGlobalVar.versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    companion object {
        /**
         * 从assets 文件夹中获取文件并读取数据
         */
        @JvmStatic
        fun getFromAssets(context: Context, fileName: String): String {
            var result = ""
            try {
                val inputStream = context.resources.assets.open(fileName)
                //获取文件的字节数
                val length = inputStream.available()
                //创建byte数组
                val buffer = ByteArray(length)
                //将文件中的数据读到byte数组中
                inputStream.read(buffer)
                result = String(buffer, Charset.forName("UTF-8"))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }
    }

    fun getSignature(context: Context): Int {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)

            val sign = packageInfo.signatures[0]
            return sign.hashCode()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return -1
    }

}