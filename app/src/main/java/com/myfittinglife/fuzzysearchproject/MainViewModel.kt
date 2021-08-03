package com.myfittinglife.fuzzysearchproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okio.ByteString
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author LD
 * @Time 2021/7/29 9:09
 * @Describe
 * @Modify
 */
class MainViewModel :ViewModel(){

    companion object{
        const val TAG="ceshi"
    }
    /**
     * 错误信息LiveData
     */
    private val errorLiveData = MutableLiveData<Any>()

    /**
     * 模糊搜索结果LiveData
     */
    private val resultLiveData = MutableLiveData<List<String>>()

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()
    private val retrofit: Retrofit =Retrofit.Builder()
        .baseUrl("http://a")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(ApiService::class.java)

    private val gson = Gson()

    fun getErrorLiveData():LiveData<Any>{
        return errorLiveData
    }
    fun getResultLiveData():LiveData<List<String>>{
        return resultLiveData
    }

    /**
     * 模糊搜索
     */
    fun getFuzzySearchList(url:String,content:String){
        api.getFuzzySearchList(url,content).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorLiveData.value = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response?.body() != null){
                    val charset = response.body()!!.contentType()!!.charset()!!

                    val source = response?.body()?.source()
                    source?.let {
                        //为啥这里选UTF_8_BOM
                        source.skip(ByteString.decodeHex("efbbbf").size().toLong())
                    }
                    val string = response?.body()?.string()
                    Log.i(TAG, "onResponse: string"+string)
                    //window.baidu.sug({q:"奥运会",p:false,s:["奥运会","奥运会女子200米蝶泳决赛","奥运会奖牌榜","奥运会赛程","奥运会金牌榜2021","奥运会乒乓球","奥运会直播","奥运会多少年举办一次","奥运会在哪看","奥运会裁判"]});
                    string?.let {
                        val startIndex = it.indexOf("(")
                        val endIndex =  it.length-2
                        //不可以使用这个，因为数据中间可能出现右括号
//                        val endIndex = it.indexOf(")")
                        //截取
                        val json = it.substring(startIndex+1, endIndex)
                        val jsonObject = gson.fromJson<ResultBean>(json, ResultBean::class.java)
                        resultLiveData.value = jsonObject.s
                    }
                }
            }
        })

    }
}