package com.huyingbao.core.common.module

/**
 * 通用常量类
 *
 * Created by liujunfeng on 2019/5/30.
 */
class CommonContants {
    object Config {
        const val PAGE_SIZE = 20
        const val HTTP_TIME_OUT = 20L
    }

    object Key {
        const val ACCESS_TOKEN = "accessToken"
        const val USER_NAME = "userName"
        const val PASSWORD = "password"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val INFO = "info"
        const val IS_TO_LOGIN = "isToLogin"
    }

    object Header {
        const val AUTHORIZATION = "Authorization"
    }

    object Address {
        const val RandomActivity = "/gan/random/RandomActivity"

        const val ArticleActivity = "/wan/article/ArticleActivity"

        const val LoginActivity = "/github/login/LoginActivity"
        const val PersonActivity = "/github/person/PersonActivity"
        const val IssueActivity = "/github/issue/IssueActivity"
        const val CodeActivity = "/github/code/CodeActivity"
        const val ReposActivity = "/github/repos/ReposActivity"
        const val MainActivity = "/github/main/MainActivity"
        const val UserActivity = "/github/user/UserActivity"
        const val StarActivity = "/github/star/StarActivity"
        const val SearchActivity = "/github/search/SearchActivity"
    }

    object Error {
        const val COMMON = 600
        const val UNAUTHORIZED = 401
    }
}