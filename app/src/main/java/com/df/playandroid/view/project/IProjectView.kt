package com.df.playandroid.view.project

import com.df.playandroid.response.category.CategoryData

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：
 */
interface IProjectView {
    fun getProjectCategorySuccess(result: List<CategoryData>)
}