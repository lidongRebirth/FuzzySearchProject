package com.myfittinglife.fuzzysearchproject

/**
 * @Author LD
 * @Time 2021/7/29 9:28
 * @Describe
 * @Modify
 */
data class ResultBean(
    var p: Boolean,
    var q: String,
    //模糊搜索的结果
    var s: List<String>
)