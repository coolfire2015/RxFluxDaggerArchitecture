package com.huyingbao.core.arch.dispatcher

import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.model.Change
import com.huyingbao.core.arch.model.Error
import com.huyingbao.core.arch.model.Loading
import com.huyingbao.core.arch.store.Store
import com.huyingbao.core.arch.view.SubscriberView
import org.greenrobot.eventbus.EventBus

/**
 * 调度核心类：
 *
 * 1.管理[SubscriberView]、[Store]订阅。
 *
 * 2.发送[Action]、[Change]、[Loading]、[Error]。
 *
 * Created by liujunfeng on 2019/1/1.
 */
class Dispatcher {
    /**
     * [Store]注册订阅。
     */
    fun <T : Store> subscribeStore(store: T) {
        EventBus.getDefault().register(store)
    }

    /**
     * [com.huyingbao.core.arch.view.SubscriberView]注册订阅。
     */
    fun <T : SubscriberView> subscriberView(subscriberView: T) {
        EventBus.getDefault().register(subscriberView)
    }

    /**
     * [Store]取消订阅。
     */
    fun <T : Store> unsubscribeStore(store: T) {
        EventBus.getDefault().unregister(store)
    }

    /**
     * [com.huyingbao.core.arch.view.SubscriberView]取消订阅。
     */
    fun <T : SubscriberView> unsubscriberView(subscriberView: T) {
        EventBus.getDefault().unregister(subscriberView)
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
     * 发送[Action]到所有订阅的[Store]。
     */
    fun postAction(action: Action) {
        EventBus.getDefault().post(action, action.tag)
    }

    /**
     * 发送[Change]到所有订阅的[com.huyingbao.core.arch.view.FluxView]，粘性通知。
     */
    fun postChange(change: Change) {
        EventBus.getDefault().postSticky(change, change.tag)
    }

    /**
     * 发送[Error]到所有订阅的[com.huyingbao.core.arch.view.FluxView]，粘性通知。
     *
     * 发送：操作完成，异常执行状态。
     */
    fun postError(error: Error) {
        EventBus.getDefault().postSticky(error)
    }

    /**
     * 发送[Loading]到所有订阅的[com.huyingbao.core.arch.view.FluxView]，粘性通知。
     *
     * 发送：操作进度。
     */
    fun postLoading(loading: Loading) {
        EventBus.getDefault().postSticky(loading)
    }
}
