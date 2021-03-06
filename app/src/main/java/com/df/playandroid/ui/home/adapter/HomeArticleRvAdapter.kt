package com.df.playandroid.ui.home.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.response.article.ArticleInfo

/**
 * 作者：PeterWu
 * 时间：2020/1/6
 * 描述：首页文章Adapter
 */
class HomeArticleRvAdapter(data: MutableList<ArticleInfo>) :
    BaseQuickAdapter<ArticleInfo, BaseViewHolder>(R.layout.rv_home_article_item, data) {
    override fun convert(
        helper: BaseViewHolder,
        item: ArticleInfo
    ) {
        helper
            .setText(R.id.item_article_title_tv, item.title)
            .setText(R.id.item_article_author_tv, item.author)
            .setText(R.id.item_article_time_tv, item.niceShareDate)
            .setText(R.id.item_article_sort_tv, item.chapterName)
        val authorTv = helper.getView<TextView>(R.id.item_article_author_tv)
        if (item.author.isNullOrEmpty()) {
            authorTv.visibility = View.GONE
        } else {
            authorTv.visibility = View.VISIBLE
        }
    }

}