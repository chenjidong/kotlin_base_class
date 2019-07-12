package com.cjd.kotlin_tool.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cjd.base.activity.BaseActivity
import com.cjd.base.event.HttpRequestEvent
import com.cjd.base.utils.LogUtils
import com.cjd.base.utils.RetrofitUtils
import com.cjd.kotlin_tool.R
import com.cjd.kotlin_tool.api.IMainApiService
import com.cjd.kotlin_tool.ui.main.adapter.MainAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity() {


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", View.OnClickListener {

                }).show()
        }

        val listener = object : com.cjd.base.listener.OnPermissionListener {
            override fun onGranted() {
                super.onGranted()
                Toast.makeText(this@MainActivity, "onGranted", Toast.LENGTH_SHORT).show()
            }

            override fun onDenied(deniedPermissions: List<String>) {
                super.onDenied(deniedPermissions)
                Toast.makeText(this@MainActivity, "onDenied", Toast.LENGTH_SHORT).show()

            }
        }
        onRuntimePermissionRequest(listener, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        Handler().postDelayed(Runnable { showWaitingDialog("123", true) }, 2000)

        RetrofitUtils.getRetrofit().create(IMainApiService::class.java).getMain()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { LogUtils.d(it.string()) },
                { LogUtils.d(it.message) },
                { LogUtils.d("compile") })

        rv.layoutManager = LinearLayoutManager(this)
        val adapter = MainAdapter(this)

        rv.adapter = adapter
        val list: MutableList<String> = ArrayList()
        for (value in 1..10) {
            list.add(value.toString())
        }
        adapter.list = list
    }

    override fun hasEvent(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(event: HttpRequestEvent) {
        LogUtils.d(event.code.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
