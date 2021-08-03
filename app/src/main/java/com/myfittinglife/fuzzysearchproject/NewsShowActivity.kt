package com.myfittinglife.fuzzysearchproject

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_news_show.*

/**
@Author LD
@Time 2021/7/29 14:01
@Describe 显示新闻的页面
@Modify
 */
class NewsShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_show)
        if (Build.VERSION.SDK_INT >= 21) {
//            window.decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }

        supportActionBar?.hide()
        initWebSetting()

        val url = intent.getStringExtra("url")
        url?.let { mWeb.loadUrl(it) }


    }

    private fun initWebSetting() {
        var webSettings: WebSettings = mWeb.settings
        webSettings.javaScriptEnabled = true
        //支持通过JS打开新窗口
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        //网页的保存数据需要使用
        webSettings.domStorageEnabled = true

        webSettings.databaseEnabled = false
        webSettings.setAppCacheEnabled(false)
        //允许访问文件
        webSettings.allowFileAccess = true
        webSettings.savePassword = false

        //缩放操作
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSettings.useWideViewPort = true

        webSettings.blockNetworkImage = false//不阻塞网络图片
        //设置访问环境
//        var r = webSettings.userAgentString
//        Log.i(TAG, "initWebSetting: $r")
//        webSettings.userAgentString=r

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //允许混合（http，https）
            //websettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE;
        }


        mWeb.webViewClient = object : WebViewClient() {

        }
    }
}