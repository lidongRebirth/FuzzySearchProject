package com.myfittinglife.fuzzysearchproject

/**
 * @Author LD
 * @Time 2021/7/29 9:35
 * @Describe
 * @Modify
 */
interface Const {

    companion object Url{
        //模糊查询地址
        const val fuzzySearchUrl  ="https://sp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su"
        //详情搜索接口
        // http://www.baidu.com.cn/s?wd=奥运会&cl=3
        const val serchUrl="http://www.baidu.com.cn/s?wd="
    }
}