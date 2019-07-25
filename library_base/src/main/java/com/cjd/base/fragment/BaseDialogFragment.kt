package com.cjd.base.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/2
 * description 弹窗 Dialog
 */
abstract class BaseDialogFragment : AppCompatDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(view)
    }

    protected abstract fun getLayoutResId(): Int

    protected abstract fun initData(view: View)
}