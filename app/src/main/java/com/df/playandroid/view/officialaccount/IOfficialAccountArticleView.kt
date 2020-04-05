package com.df.playandroid.view.officialaccount

import com.df.playandroid.response.officialaccount.OfficialAccountArticleData

/**
 * 作者：PeterWu
 * 时间：2020/4/4
 * 描述：
 */
interface IOfficialAccountArticleView {
    fun showLoadingView()
    fun hideLoadingView()
    fun stopRefresh()
    fun stopLoadMore()
    fun getArticleSuccess(result: OfficialAccountArticleData)
    fun loadMoreArticleSuccess(result: OfficialAccountArticleData)
}