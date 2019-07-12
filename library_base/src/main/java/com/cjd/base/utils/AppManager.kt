package com.cjd.base.utils

import android.app.Activity
import java.util.*

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/1
 * description 业务逻辑栈
 */
object AppManager {
    private val activityStack: Stack<Activity> = Stack()

    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity) {
        if (activityStack.contains(activity))
            activityStack.remove(activity)
    }

    fun finishActivity(activity: Activity) {
        if (activityStack.contains(activity))
            activity.finish();
    }

    fun finishActivity(clazz: Class<Activity>): Unit {
        val del: Activity? = activityStack.lastOrNull { clazz == it.javaClass }
        del?.finish()
    }

    fun peekActivity(): Activity? {
        if (activityStack.size <= 0)
            return null
        return activityStack.peek()
    }

    fun <T : Activity> getActivity(clazz: Class<Activity>): T? {
        var target: T? = null
        activityStack.filter { it == clazz }.forEach {
            @Suppress("UNCHECKED_CAST")
            target = it as T
        }
        return target
    }
}