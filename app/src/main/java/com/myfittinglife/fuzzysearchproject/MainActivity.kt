package com.myfittinglife.fuzzysearchproject

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


/**
@Author LD
@Time 2021/7/28 15:03
@Describe 模糊搜索框实现
@Modify
 */
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ceshi"
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            MainViewModel::class.java
        )
    }
    private val data = mutableListOf<String>()
    private val myAdapter = MyAdapter(data)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
        supportActionBar?.hide()

        recy.layoutManager = LinearLayoutManager(this)
        recy.adapter = myAdapter
        myAdapter.setOnMyClickListener(object :MyAdapter.MyClickListener{
            override fun onClick(position: Int) {
                val intent = Intent(this@MainActivity,NewsShowActivity::class.java)
                val url = Const.serchUrl+data[position]+"&cl=3"
                intent.putExtra("url",url)
                startActivity(intent)
                etContent.setText("")
                recy.visibility = View.GONE
            }

        })

        //跳转后清空搜索框
        tvSearch.setOnClickListener {

            val intent = Intent(this,NewsShowActivity::class.java)
            val url = Const.serchUrl+etContent.text.toString()+"&cl=3"
            intent.putExtra("url",url)
            startActivity(intent)
            recy.visibility = View.GONE
            etContent.setText("")
        }
        //错误监听
        mainViewModel.getErrorLiveData().observe(this, Observer {
            Log.i(TAG, "onCreate: 访问错误：$it")
        })
        //结果监听
        mainViewModel.getResultLiveData().observe(this, Observer {
            Log.i(TAG, "onCreate: 模糊搜索结果为：$it")
            if(it.isEmpty()){
                Log.i(TAG, "onCreate: 数据为空")
                recy.visibility = View.GONE
            }else{
                recy.visibility = View.VISIBLE
            }
            data.clear()
            data.addAll(it)
            myAdapter.notifyDataSetChanged()

        })

        etContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged: ")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i(TAG, "beforeTextChanged: ")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i(TAG, "onTextChanged: ")
                if (etContent.text.isNullOrEmpty()) {
                    ivDel.visibility = View.GONE
                    //为空时如果不隐藏， 上一次搜索的结果就会在下面显示不会隐藏
                    recy.visibility = View.GONE
                } else {
                    ivDel.visibility = View.VISIBLE
//                    recy.visibility = View.VISIBLE
                    Log.i(TAG, "onTextChanged: 搜索:${etContent.text}")
                    mainViewModel.getFuzzySearchList(Const.fuzzySearchUrl,etContent.text.toString())
                }
            }
        })


        //删除按键
        ivDel.setOnClickListener {
            ivDel.visibility = View.GONE
            etContent.setText("")
        }
    }
}