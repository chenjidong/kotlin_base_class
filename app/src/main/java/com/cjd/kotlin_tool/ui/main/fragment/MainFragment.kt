package com.cjd.kotlin_tool.ui.main.fragment

import android.view.View
import com.cjd.base.fragment.BaseFragment
import com.cjd.kotlin_tool.R
import kotlinx.android.synthetic.main.main_fragment_main.*

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/3
 * description
 */
class MainFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.main_fragment_main

    override fun initData(view: View) {
        tv_content.text = "123123"
    }
}