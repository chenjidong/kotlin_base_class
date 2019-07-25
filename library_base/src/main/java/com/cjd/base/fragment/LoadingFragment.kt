package com.cjd.base.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.cjd.base.R

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/3
 * description
 */
class LoadingFragment : BaseDialogFragment() {

    override fun getLayoutResId() = R.layout.base_dialog_loading

    override fun initData(view: View) {
        init(view)
    }

    companion object {
        @JvmStatic
        fun newInstance(text: String, isCancelable: Boolean): LoadingFragment {
            val fragment = LoadingFragment()
            val bundle = Bundle()
            bundle.putString("alert", text)
            fragment.arguments = bundle
            fragment.isCancelable = isCancelable
            return fragment
        }
    }

    private fun init(view: View) {
        val text: TextView = view.findViewById(R.id.tv_loading)
        if (arguments != null && !TextUtils.isEmpty(arguments!!.getString("alert"))) {
            text.visibility = View.VISIBLE
            text.text = arguments!!.getString("alert")
        } else
            text.visibility = View.GONE
    }
}