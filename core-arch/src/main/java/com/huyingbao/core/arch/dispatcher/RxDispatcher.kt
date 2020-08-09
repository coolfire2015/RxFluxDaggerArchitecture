package com.huyingbao.core.arch.dispatcher

import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.arch.store.RxStore
import com.huyingbao.core.arch.view.RxSubscriberView
import org.greenrobot.eventbus.EventBus

/**
 * 调度核心类：
 *
 * 1.管理[RxSubscriberView]、[RxStore]订阅。
 *
 * 2.发送[RxAction]、[RxChange]、[RxLoading]、[RxError]、[RxRetry]。
 *
 * Created by liujunfeng on 2019/1/1.
 */
class RxDispatcher {
    /**
     * [RxStore]注册订阅。
     */
    fun <T : RxStore> subscribeRxStore(rxStore: T) {
        EventBus.getDefault().register(rxStore)
    }

    /**
     * [com.huyingbao.core.arch.view.RxFluxView]注册订阅。
     */
    fun <T : RxSubscriberView> subscribeRxView(rxView: T) {
        EventBus.getDefault().register(rxView)
    }

    /**
     * [RxStore]取消订阅。
     */
    fun <T : RxStore> unsubscribeRxStore(rxStore: T) {
        EventBus.getDefault().unregister(rxStore)
    }

    /**
     * [com.huyingbao.core.arch.view.RxFluxView]取消订阅。
     */
    fun <T : RxSubscriberView> unsubscribeRxView(rxView: T) {
        EventBus.getDefault().unregister(rxView)
    }

    /**
     * 判断是否注册订阅。
     */
    fun <T> isSubscribe(t: T): Boolean {
        return EventBus.getDefault().isRegistered(t)
    }

    /**
     * 取消所有订阅。
     */
    @Synchronized
    fun unsubscribeAll() {
        EventBus.clearCaches()
        EventBus.getDefault().removeAllStickyEvents()
    }

    /**
     * 发送[RxAction]到所有订阅的[RxStore]。
     */
    fun postRxAction(rxAction: RxAction) {
        EventBus.getDefault().post(rxAction, rxAction.tag)
    }

    /**
     * 发送[RxChange]到所有订阅的[com.huyingbao.core.arch.view.RxFluxView]，粘性通知。
     */
    fun postRxChange(rxChange: RxChange) {
        EventBus.getDefault().postSticky(rxChange, rxChange.tag)
    }

    /**
     * 发送[RxError]到所有订阅的[com.huyingbao.core.arch.view.RxFluxView]，粘性通知。
     *
     * 发送：操作完成，异常执行状态。
     */
    fun postRxError(rxError: RxError) {
        EventBus.getDefault().postSticky(rxError)
    }

    /**
     * 发送[RxLoading]到所有订阅的[com.huyingbao.core.arch.view.RxFluxView]，粘性通知。
     *
     * 发送：操作进度。
     */
    fun postRxLoading(rxLoading: RxLoading) {
        EventBus.getDefault().postSticky(rxLoading)
    }
}
