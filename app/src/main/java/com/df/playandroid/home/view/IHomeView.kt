package com.df.playandroid.home.view

import com.df.playandroid.home.response.BannerResponse
import com.df.playandroid.home.response.HomeArticleResponse
import com.df.playandroid.home.response.SearchHotWordResponse

interface IHomeView {
    fun getBannerSuccess(result: List<BannerResponse.BannerData>)
    fun stopRefresh()
    fun getArticleSuccess(result: HomeArticleResponse.ArticleData)
    fun stopLoadMore()
    fun loadMoreArticleSuccess(result: HomeArticleResponse.ArticleData)
    fun getHotWordSuccess(result: List<SearchHotWordResponse.SearchHotWordData>)
}