package com.df.playandroid.ui.project.fragment

import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.df.playandroid.R
import com.df.playandroid.base.event.BaseEvent
import com.df.playandroid.base.event.EventManager
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.presenter.project.ProjectPresenter
import com.df.playandroid.response.category.CategoryData
import com.df.playandroid.ui.project.adapter.ProjectPageAdapter
import com.df.playandroid.ui.search.activity.SearchActivity
import com.df.playandroid.view.project.IProjectView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.base_search_header.*
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：项目Fragment
 */
class ProjectFragment : BaseFragment<IProjectView, ProjectPresenter>(), IProjectView,
    TabLayout.OnTabSelectedListener {

    override fun getLayoutId() = R.layout.fragment_project

    override fun setupPresenter() = ProjectPresenter(requireContext())

    override fun initView() {
        mHeaderTitle.text = getString(R.string.main_project_recommend)
        mTabLayout.addOnTabSelectedListener(this)
        mSearchRl.setOnClickListener {
            launch<SearchActivity>()
            activity?.overridePendingTransition(0, 0)
        }
    }
    override fun initData() {
        mPresenter?.getProjectCategory()
    }

    override fun getProjectCategorySuccess(result: List<CategoryData>) {
        val fragments: ArrayList<ProjectListFragment> = ArrayList()
        for (i in result.indices) {
            val page = ProjectListFragment.newInstance(result[i].id)
            fragments.add(page)
        }
        mViewPager.adapter = ProjectPageAdapter(childFragmentManager, lifecycle, fragments)
        TabLayoutMediator(mTabLayout, mViewPager, false,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                val tabView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_tab_custom, null)
                val tabTv = tabView.findViewById<TextView>(R.id.mTabTv)
                tabTv.text = result[position].name
                tab.customView = tabView
            }).attach()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        val title = tab?.customView?.findViewById<TextView>(R.id.mTabTv)
        title?.run {
            textSize = 14.0f
            typeface = Typeface.DEFAULT
            setTextColor(ContextCompat.getColor(requireContext(), R.color.color_main_sub_text))
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val title = tab?.customView?.findViewById<TextView>(R.id.mTabTv)
        title?.run {
            textSize = 18.0f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mainColor))
        }
    }

    override fun hadEventBus() = true

    override fun onMessageEvent(event: BaseEvent) {
        if (event is EventManager.SendHotWordEvent) {
            val words = event.words
            for (i in words.indices) {
                val hotTv = TextView(requireContext())
                hotTv.text = words[i].name
                hotTv.textSize = 14f
                hotTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_main_sub_text
                    )
                )
                hotTv.gravity = Gravity.CENTER_VERTICAL
                mFlipper.addView(hotTv)
            }
        }
    }
}