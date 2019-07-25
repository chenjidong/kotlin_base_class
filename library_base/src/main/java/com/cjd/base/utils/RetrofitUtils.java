package com.cjd.base.utils;

import android.content.Context;
import android.text.TextUtils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description retrofit 封装
 */
public class RetrofitUtils {

    private static Retrofit retrofit = null;
    private static String BASE_URL = null;
    private static RetrofitUtils instance;

    private RetrofitUtils() {

    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public static void init(Context context, String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new RuntimeException("baseUrl not null");
        }
        if (retrofit == null) {
            BASE_URL = baseUrl;
            OkHttpUtils.INSTANCE.init(context);

            retrofit = new Retrofit.Builder().client(OkHttpUtils.INSTANCE.getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
    }

    public static RetrofitUtils getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtils.class) {
                if (instance == null) {
                    instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null)
            throw new RuntimeException("please on application init!");
        return retrofit;
    }

    public <T> T create(Class<T> clazz) {
        return getRetrofit().create(clazz);
    }
}
