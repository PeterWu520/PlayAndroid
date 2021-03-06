package com.df.playandroid.base.event

import com.df.playandroid.response.home.SearchHotWordData
import com.df.playandroid.response.user.UserData

/**
 * 作者：PeterWu
 * 时间：2020/4/19
 * 描述：
 */
object EventManager {

    /**
     * 更新用户信息
     */
    class UpdateUserInfo(val userInfo: UserData) : BaseEvent()

    /**
     * 注册成功
     */
    class RegisterSuccessEvent(val username: String, val pwd: String, val icon: String?) : BaseEvent()

    /**
     * 更新个人主页
     */
    class UpdateUserPage : BaseEvent()

    /**
     * 发送搜索热词
     */
    class SendHotWordEvent(val words: List<SearchHotWordData>) : BaseEvent()

    /**
     * 搜索公众号文章
     */
    class SearchWxArticleEvent(val keyword: String): BaseEvent()

    /**
     * 清空公众号搜索列表
     */
    class CleanWxSearchArticleEvent: BaseEvent()
}