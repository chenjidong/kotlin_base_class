package com.cjd.kotlin_tool.ui.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjd.kotlin_tool.R
import com.cjd.kotlin_tool.ui.main.adapter.holder.MainHolder

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description
 */
class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainHolder>() {
    var list: List<String> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_item_list, p0, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MainHolder, p1: Int) {

        p0.tvContent?.text = list[p1]
    }
}