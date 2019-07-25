package com.cjd.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjd.base.holder.BaseHolder
import com.cjd.base.listener.OnItemClickListener

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/20
 * description
 */
abstract class BaseQuickAdapter<T>(val context: Context, private val layoutResId: Int) : BaseArrayAdapter<T, BaseHolder>() {
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseHolder {
        val view = LayoutInflater.from(context).inflate(layoutResId, viewGroup, false)
        val baseHolder = BaseHolder(view)
        view.setOnClickListener {
            onItemClickListener?.onItemClick(it, baseHolder.layoutPosition)
        }
        return baseHolder
    }

    override fun onBindViewHolder(baseHolder: BaseHolder, position: Int) {
        convert(baseHolder, getItem(position))
    }

    protected abstract fun convert(baseHolder: BaseHolder, item: T)
}