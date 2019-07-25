package com.cjd.base.adapter

import android.support.v7.widget.RecyclerView
import com.cjd.base.holder.BaseHolder

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/20
 * description
 */
abstract class BaseArrayAdapter<T, VH : BaseHolder> : RecyclerView.Adapter<VH>() {

    var currentItems: MutableList<T> = mutableListOf()

    fun add(data: T) {
        currentItems.add(data)
        notifyDataSetChanged()
    }

    fun add(index: Int, data: T) {
        currentItems.add(index, data)
        notifyDataSetChanged()
    }

    open fun addAll(collection: Collection<T>?) {
        if (collection != null) {
            currentItems.addAll(collection)
            notifyDataSetChanged()
        }
    }

    fun replaceAll(collection: Collection<T>) {
        currentItems.clear()
        addAll(collection)
    }

    fun getItem(position: Int): T {
        return currentItems[position]
    }

    override fun getItemCount(): Int {
        return currentItems.size
    }
}