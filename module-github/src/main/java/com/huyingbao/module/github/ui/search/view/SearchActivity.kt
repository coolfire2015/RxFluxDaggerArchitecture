package com.huyingbao.module.github.ui.search.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.search.store.SearchStore

/**
 * 搜索模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class SearchActivity : BaseRxFragActivity<SearchStore>() {
    override fun createFragment(): Fragment? {
        return SearchFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
