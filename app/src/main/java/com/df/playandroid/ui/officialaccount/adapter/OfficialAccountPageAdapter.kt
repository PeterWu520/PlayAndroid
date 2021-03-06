package com.df.playandroid.ui.officialaccount.adapter

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.df.playandroid.ui.officialaccount.fragment.OfficialAccountArticleFragment

/**
 * 作者：PeterWu
 * 时间：2020/4/4
 * 描述：
 */
class OfficialAccountPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments: ArrayList<OfficialAccountArticleFragment>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}