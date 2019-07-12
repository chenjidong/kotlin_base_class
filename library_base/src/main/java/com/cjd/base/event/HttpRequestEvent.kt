package com.cjd.base.event

import java.net.HttpURLConnection

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description
 */
class HttpRequestEvent(var code: Int = HttpURLConnection.HTTP_OK) : BaseEvent()