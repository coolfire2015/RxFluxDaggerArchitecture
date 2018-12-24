package com.huyingbao.module.wan;

import com.huyingbao.core.common.CommonApp;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class WanApplication extends CommonApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerWanComponent.builder().application(this).build();
    }
}