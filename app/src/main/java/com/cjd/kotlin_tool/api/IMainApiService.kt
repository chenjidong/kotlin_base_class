package com.cjd.kotlin_tool.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description
 */
interface IMainApiService {

    @GET("/api/tag/random")
    fun getMain(): Observable<ResponseBody>
}