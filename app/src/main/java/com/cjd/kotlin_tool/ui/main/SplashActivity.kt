package com.cjd.kotlin_tool.ui.main

import android.content.Intent
import android.os.Handler
import com.cjd.base.activity.BaseActivity
import com.cjd.kotlin_tool.R

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description
 */
class SplashActivity : BaseActivity() {
    override fun getLayoutResId() = R.layout.main_activity_splash

    override fun initData() {
        init()
    }

    private fun init() {
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 2000)
    }
}