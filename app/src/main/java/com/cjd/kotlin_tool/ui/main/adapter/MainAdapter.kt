package com.cjd.kotlin_tool.ui.main.adapter

import android.content.Context
import com.cjd.base.adapter.BaseQuickAdapter
import com.cjd.base.holder.BaseHolder
import com.cjd.kotlin_tool.R
import kotlinx.android.synthetic.main.main_item_list.view.*

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/4
 * description
 */
class MainAdapter(context: Context) : BaseQuickAdapter<String>(context, R.layout.main_item_list) {
    override fun convert(baseHolder: BaseHolder, item: String) {
        baseHolder.itemView.tv_content?.text = item
    }
}