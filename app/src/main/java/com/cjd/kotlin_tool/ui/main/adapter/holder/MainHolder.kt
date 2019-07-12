package com.cjd.kotlin_tool.ui.main.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.cjd.kotlin_tool.R

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description
 */
class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvContent: TextView? = null
    init {
        tvContent = itemView.findViewById(R.id.tv_content)
    }

}