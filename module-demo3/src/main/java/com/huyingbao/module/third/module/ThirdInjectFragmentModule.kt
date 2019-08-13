package com.huyingbao.module.third.module


import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.third.ui.view.ThirdFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class ThirdInjectFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectThirdFragment(): ThirdFragment
}
