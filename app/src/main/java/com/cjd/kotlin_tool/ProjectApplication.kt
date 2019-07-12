package com.cjd.kotlin_tool

import com.cjd.base.BaseApplication
import com.cjd.base.utils.RetrofitUtils

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description
 */
class ProjectApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        RetrofitUtils.init(this, "http://callshow.buypanamera.com")
    }
}