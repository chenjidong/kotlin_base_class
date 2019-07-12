package com.cjd.base.holder

import android.view.View
import com.cjd.base.listener.OnItemClickListener

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/2
 * description
 */
class BaseClickHolder(itemView: View) : BaseHolder(itemView), View.OnClickListener {
    private var onItemClickListener: OnItemClickListener? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        v?.let { onItemClickListener?.onItemClick(it, layoutPosition) }
    }
}