package com.cjd.base.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.cjd.base.fragment.LoadingFragment
import com.cjd.base.listener.OnPermissionListener
import com.cjd.base.utils.AppManager
import com.cjd.base.utils.LogUtils
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/2
 * description activity 基类
 */
abstract class BaseActivity : AppCompatActivity() {
    private val waitingTag: String = "Waiting"
    private val requestPermissionSendCode = 10010
    private var permissionListener: OnPermissionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        AppManager.addActivity(this)
        if (hasEvent())
            EventBus.getDefault().register(this)
        initData()
    }

    protected abstract fun getLayoutResId(): Int

    protected abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        AppManager.removeActivity(this)
        if (hasEvent())
            EventBus.getDefault().unregister(this)
    }

    open fun hasEvent(): Boolean {
        return false
    }

    /**
     * 请求权限
     */
    fun onRuntimePermissionRequest(listener: OnPermissionListener?, vararg permissions: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            listener?.onGranted()
            return
        }
        permissionListener = listener
        val deniedPermissions = ArrayList<String>()//存储被拒绝的权限
        val top = AppManager.peekActivity() ?: return

        for (p in permissions) {
            if (ContextCompat.checkSelfPermission(top, p) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(p)
            }
        }
        if (deniedPermissions.isEmpty()) {
            listener?.onGranted()
        } else {
            ActivityCompat.requestPermissions(top, deniedPermissions.toTypedArray(), requestPermissionSendCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestPermissionSendCode == requestCode) {
            val deniedPermissionList = ArrayList<String>()
            if (grantResults.isNotEmpty()) {
                for (i in grantResults) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissionList.add(permissions[i])
                    }
                }
                if (deniedPermissionList.isEmpty()) {
                    //权限全部同意处理
                    permissionListener?.onGranted()
                } else {
                    //存在拒绝权限处理
                    permissionListener?.onDenied(deniedPermissionList)
                }
            }
        }
    }

    fun showWaitingDialog(text: String, isCancelable: Boolean) {
        var dialog: LoadingFragment? = supportFragmentManager.findFragmentByTag(waitingTag) as LoadingFragment?
        if (dialog == null) {
            dialog = LoadingFragment.newInstance(text, isCancelable)
        }
        if (!dialog.isAdded) {
            supportFragmentManager.beginTransaction()
                    .add(dialog, waitingTag)
                    .commitAllowingStateLoss()
        }
    }

    fun dismissWaitingDialog() {
        val dialog: LoadingFragment? = supportFragmentManager.findFragmentByTag(waitingTag) as LoadingFragment
        dialog?.let { supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss() }
    }
}