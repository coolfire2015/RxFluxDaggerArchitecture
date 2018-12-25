package com.huyingbao.core.arch.store;

import com.huyingbao.core.arch.model.RxAction;

import androidx.lifecycle.LifecycleObserver;

/**
 * This interface must be implemented by the store
 * 所有的store必须实现该接口
 */
public interface RxActionDispatch extends LifecycleObserver {

    /**
     * store在接收到RxAction时,调用该方法
     *
     * @param action
     */
    void onRxAction(RxAction action);
}
