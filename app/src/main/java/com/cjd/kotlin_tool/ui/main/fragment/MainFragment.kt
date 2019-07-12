package com.cjd.kotlin_tool.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_content.text = "123123"
    }
}