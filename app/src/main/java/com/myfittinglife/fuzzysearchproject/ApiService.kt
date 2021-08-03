package com.myfittinglife.fuzzysearchproject

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @Author LD
 * @Time 2021/7/29 9:12
 * @Describe
 * @Modify
 */
interface ApiService {

    @GET
    fun getFuzzySearchList(@Url url:String,@Query("wd") content:String): Call<ResponseBody>
}