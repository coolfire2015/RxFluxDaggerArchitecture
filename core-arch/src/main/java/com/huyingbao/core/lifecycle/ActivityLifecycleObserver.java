package com.huyingbao.core.lifecycle;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.huyingbao.core.dispatcher.RxViewDispatch;
import com.huyingbao.core.store.RxStore;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/13.
 */
public class ActivityLifecycleObserver implements LifecycleObserver {
    private final Activity mActivity;
    @Inject
    DispatchingAndroidInjector<Fragment> mSupportFragmentInjector;

    public ActivityLifecycleObserver(Activity activity) {
        this.mActivity = activity;
        if (activity instanceof RxFluxActivity) {
            ((RxFluxActivity) activity).mSupportFragmentInjector = mSupportFragmentInjector;
            AndroidInjection.inject(activity);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        if (mActivity instanceof RxViewDispatch) {
            List<RxStore> rxStoreList = ((RxViewDispatch) mActivity).getLifecycleRxStoreList();
            if (rxStoreList != null && rxStoreList.size() > 0)
                for (RxStore rxStore : rxStoreList)
                    ((FragmentActivity) mActivity).getLifecycle().addObserver(rxStore);
        }
    }
}
