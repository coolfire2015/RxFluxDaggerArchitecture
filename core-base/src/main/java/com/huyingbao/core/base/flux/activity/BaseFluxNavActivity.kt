package com.huyingbao.core.base.flux.activity

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.Navigation
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.base.R
import com.huyingbao.core.utils.setNavigation


/**
 * 使用Dagger.Android，持有ViewModel，自动管理订阅
 *
 * 使用[Navigation]管理Fragment
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseFluxNavActivity<T : RxActivityStore> :
        BaseFluxActivity<T>() {
    /**
     * 使用默认Activity布局，可以覆盖该方法，使用自定义布局
     */
    override fun getLayoutId(): Int {
        return R.layout.base_activity_nav
    }

    /**
     * 自定义[Navigation]资源文件
     */
    @NavigationRes
    protected abstract fun getGraphId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigation(getFragmentContainerId(), getGraphId())
    }
}