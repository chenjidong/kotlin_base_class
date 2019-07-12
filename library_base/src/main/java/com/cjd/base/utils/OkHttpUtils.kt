package com.cjd.base.utils

import android.content.Context
import com.cjd.base.ParseInterceptor
import com.cjd.base.event.HttpRequestEvent
import com.cjd.base.preferences.PreferencesHelper
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description okhttp3 封装
 */
object OkHttpUtils {
    private var client: OkHttpClient? = null
    private const val CONNECT_TIME_OUE = 10
    private const val WRITE_TIME_OUT = 15
    private const val READ_TIME_OUT = 10
    private const val CACHE_DIRECTORY = "_http_cache"
    private const val CACHE_SIZE = 32 * 1024 * 1024

    const val AUTH = "Auth"
    const val USER_AGENT = "User-Agent"
    const val CACHE_CONTROL = "Cache-Control"//HTTP 1.1
    const val CACHE_CONTROL_ONLY_CACHED = "public, only-if-cached, max-stale=2419200"
    const val CACHE_CONTROL_NETWORK = "public,max-age=0"
    const val PRAGMA = "Pragma"//HTTP 1.0+

    fun getClient(): OkHttpClient? {
        if (client == null) throw RuntimeException("Please on application init()!")
        return client
    }

    fun init(context: Context) {
        if (client == null) {
            synchronized(OkHttpUtils::class) {
                if (client == null) {
                    val dispatcher = Dispatcher(Executors.newFixedThreadPool(20))
                    dispatcher.maxRequests = 20
                    dispatcher.maxRequestsPerHost = 1

                    val cacheFile = File(context.cacheDir, CACHE_DIRECTORY)
                    val cache = Cache(cacheFile, CACHE_SIZE.toLong())
                    client = OkHttpClient.Builder()
                        .dispatcher(dispatcher)
                        .cache(cache).connectTimeout(CONNECT_TIME_OUE.toLong(), TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
                        .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
                        .connectionPool(ConnectionPool(100, 30, TimeUnit.SECONDS))
                        .addInterceptor(LogInterceptor())
                        .addNetworkInterceptor(AuthInterceptor(context))
                        .build()
                }
            }
        }
    }

    /**
     * aes 加解密
     */
    private class AesInterceptor : ParseInterceptor() {

        override fun encrypt(response: String?): String {

            return response.toString()
        }

        override fun decrypt(response: String?): String {
            return response.toString()
        }

    }

    private class AuthInterceptor(private val context: Context) : Interceptor {
        private var request: Request? = null
        private var builder: Request.Builder? = null

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            request = chain.request()
            builder = request?.newBuilder()

            val auth: String? = PreferencesHelper.getString(context, AUTH, "")
            builder?.addHeader(AUTH, auth)

            request = builder?.build()

            return chain.proceed(request)
        }
    }

    private class LogInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            if (response.code() != HttpURLConnection.HTTP_OK) {
                EventBus.getDefault().post(HttpRequestEvent(response.code()))
            }
            LogUtils.d(GsonUtils.instance().toJson(response.body()))

            return response
        }

    }


}