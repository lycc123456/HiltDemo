package com.syr.hiltdemo.module.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.syr.hiltdemo.R
import com.syr.hiltdemo.databinding.ActivityMainBinding
import com.syr.hiltdemo.module.home.HomeActivity
import com.syr.hiltdemo.utils.showToast
import com.syr.module_common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var mHandler: Handler
    private lateinit var threadHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showToast("我是自定义的toast")

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        binding.viewModel = viewModel
        viewModel.toHome.observe(this, {
            startActivity(Intent(this, HomeActivity::class.java))
        })

        mHandler = Companion.MyHandler(this) {
            when (it.what) {
                0 -> {
                    Log.e("okhttp", "0 接收UI线程发送的消息")
                }
                else -> {
                    Log.e("okhttp", "else 接收子线程的发送消息")
                }
            }
        }
        mHandler.sendEmptyMessage(0)
        Log.e("okhttp", "0 Thread.name=${Thread.currentThread().name}")
        thread {
            Log.e("okhttp", "1 Thread.name=${Thread.currentThread().name}")
            mHandler.sendMessage(mHandler.obtainMessage(111))
            Looper.prepare()
            threadHandler = Companion.MyHandler(this) {
                when (it.what) {
                    0 -> {
                        Log.e("okhttp", "子线程 0 接收子线程发送的消息")
                    }
                    1 -> {
                        Log.e("okhttp", "子线程 1 接收子线程发送的消息")
                    }
                    else -> {
                        Log.e("okhttp", "子线程 else 接收主线程发送的消息")
                    }
                }
            }
            threadHandler.sendEmptyMessage(0)
            Looper.loop()
        }

        GlobalScope.launch {
            delay(1000)
            Log.e("okhttp", "2 Thread.name=${Thread.currentThread().name}")
            threadHandler.sendEmptyMessage(1)
        }
        binding.tvHandler.setOnClickListener {
            Log.e("okhttp", "3 Thread.name=${Thread.currentThread().name}")
            threadHandler.sendEmptyMessage(111)
        }
    }
}
