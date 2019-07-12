package com.cjd.base.listener

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/3
 * description
 */
open interface OnPermissionListener {

    fun onGranted() {

    }

    fun onDenied(deniedPermissions: List<String>) {

    }
}