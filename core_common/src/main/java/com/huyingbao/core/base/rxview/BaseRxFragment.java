package com.huyingbao.core.base.rxview;

import android.content.Context;

import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.base.view.BaseFragment;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseRxFragment<T extends ViewModel> extends BaseFragment implements RxFluxView {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private T mStore;

    @Nullable
    @Override
    public T getRxStore() {
        if (mStore != null) {
            return mStore;
        }
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return null;
        }
        Class<T> tClass = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(tClass);
        return mStore;
    }

    /**
     * 子类都需要在Module中使用dagger.android中的
     * {@link dagger.android.ContributesAndroidInjector}注解
     * 生成对应的注入器，在方法中进行依赖注入操作。
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        //依赖注入
        try {
            AndroidSupportInjection.inject(this);
        } catch (IllegalArgumentException e) {
            Logger.e("依赖注入失败");
        }
        super.onAttach(context);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Override
    @Subscribe()
    public void onRxError(@NonNull RxError rxError) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxError);
    }

    /**
     * 接收RxLoading，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Override
    @Subscribe()
    public void onRxRetry(@NonNull RxRetry rxRetry) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxRetry);
    }

    /**
     * 接收RxLoading，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Override
    @Subscribe()
    public void onRxLoading(@NonNull RxLoading rxLoading) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxLoading);
    }
}
