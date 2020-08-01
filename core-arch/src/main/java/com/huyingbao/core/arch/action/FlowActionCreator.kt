package com.huyingbao.core.arch.action

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusEvent
import java.util.logging.Level


/**
 * 实现View中调用的交互方法。
 *
 * 创建[RxAction]，直接发送给[com.huyingbao.core.arch.store.RxStore]。
 *
 * 创建[RxChange]、[RxLoading]、[RxError]、[RxRetry]，直接发送给[com.huyingbao.core.arch.view.RxFluxView]。
 *
 * 类似MVP模式中的Presenter，只是方法执行完成没有回调方法。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class FlowActionCreator(
        private val rxDispatcher: RxDispatcher,
        private val rxActionManager: FlowActionManager) {
    /**
     * 创建新的[RxAction]
     *
     * @param tag  tag对应具体是什么样的方法
     * @param data 可变长度参数，键值对型的参数pair-value(String-Object))
     */
    protected fun newRxAction(tag: String,
                              vararg data: Any): RxAction {
        require(data.size % 2 == 0) { "Data must be a valid list of key,value pairs" }
        val actionBuilder = RxAction.Builder(tag)
        var i = 0
        while (i < data.size) {
            val key = data[i++] as String
            val value = data[i++]
            actionBuilder.put(key, value)
        }
        return actionBuilder.build()
    }

    /**
     * [RxActionManager]是否已存在[RxAction]
     */
    protected fun hasRxAction(rxAction: RxAction): Boolean {
        return rxActionManager.contains(rxAction)
    }

    /**
     * [RxActionManager]添加[RxAction]和[Disposable]
     */
    protected fun addRxAction(rxAction: RxAction,
                              job: Job) {
        rxActionManager.add(rxAction, job)
    }

    /**
     * [RxActionManager]移除[RxAction]，停止对应的[Disposable]，
     * 被观察者[Observable]正在运行的方法会被停止。
     */
    protected fun removeRxAction(rxAction: RxAction) {
        rxActionManager.remove(rxAction)
    }

    /**
     * IO线程运行被观察者[Observable]方法， 主线程观察者[io.reactivex.Observer]接收并封装方法结果到[RxAction]。
     *
     * 关联[RxAction]与[Disposable]。
     *
     * @param canShowLoading true:有进度显示,false:无进度显示
     * @param canRetry       true:操作异常可重试,false:操作异常抛异常
     */
    private fun <T> postRxAction(rxAction: RxAction,
                                 httpObservable: Flow<T>,
                                 canShowLoading: Boolean,
                                 canRetry: Boolean) {
        if (hasRxAction(rxAction)) {
            return
        }

        httpObservable
                // 指定IO线程
                .flowOn(Dispatchers.IO)
                // 调用开始
                .onStart {
                    if (canShowLoading) {
                        //发送RxLoading(显示)事件
                        postRxLoading(rxAction, true)
                    }
                }
                // 操作进行中
                .onEach {

                    rxAction.setResponse(it)
                    postRxAction(rxAction)
                }
                // 调用结束
                .onCompletion {
                    if (canShowLoading) {
                        // 发送RxLoading(消失)事件
                        postRxLoading(rxAction, false)
                    }
                    // 移除事件
                    removeRxAction(rxAction)
                }
                // 捕获异常
                .catch {
                    // 操作异常，打印错误日志
                    EventBus.getDefault().logger.log(Level.SEVERE, "RxActionCreator onError:", it)
                    if (canRetry) {
//                        postRxRetry(rxAction, t, it)
                    } else {
                        postRxError(rxAction, it)
                    }
                    removeRxAction(rxAction)
                }
                // 指定主线程执行collect操作
                .launchIn(MainScope())
                // 添加Job到action
                .let {
                    addRxAction(rxAction, it)
                }
    }

    /**
     * [RxDispatcher]发送[RxAction]
     */
    protected open fun postRxAction(rxAction: RxAction) {
        rxDispatcher.postRxAction(rxAction)
    }

    /**
     * [RxDispatcher]发送[RxChange]
     */
    protected open fun postRxChange(rxChange: RxChange) {
        rxDispatcher.postRxChange(rxChange)
    }

    /**
     * [RxDispatcher]发送[RxLoading]
     *
     * @param isLoading true:显示，false:消失
     */
    protected open fun postRxLoading(busEvent: EventBusEvent,
                                     isLoading: Boolean) {
        rxDispatcher.postRxLoading(RxLoading.newInstance(busEvent, isLoading))
    }

    /**
     * [RxDispatcher]发送[RxError]
     */
    protected open fun postRxError(busEvent: EventBusEvent,
                                   throwable: Throwable) {
        rxDispatcher.postRxError(RxError.newInstance(busEvent, throwable))
    }

    /**
     * 异步执行，成功发送[RxAction]，异常发送[RxError]。
     */
    protected open fun <T> postHttpAction(rxAction: RxAction,
                                          httpObservable: Flow<T>) {
        postRxAction(rxAction, httpObservable, canShowLoading = false, canRetry = false)
    }

    /**
     * 异步执行，成功发送[RxAction]，异常发送[RxError]。
     * 开始结束发送[RxLoading]。
     */
    protected open fun <T> postHttpLoadingAction(rxAction: RxAction,
                                                 httpObservable: Flow<T>) {
        postRxAction(rxAction, httpObservable, canShowLoading = true, canRetry = false)
    }

    /**
     * 异步执行，成功发送[RxAction]，异常发送[RxRetry]。
     */
    protected open fun <T> postHttpRetryAction(rxAction: RxAction,
                                               httpObservable: Flow<T>) {
        postRxAction(rxAction, httpObservable, canShowLoading = false, canRetry = true)
    }

    /**
     * 异步执行，成功发送[RxAction]，异常发送[RxRetry]。
     * 开始结束发送[RxLoading]。
     */
    protected open fun <T> postHttpRetryAndLoadingAction(rxAction: RxAction,
                                                         httpObservable: Flow<T>) {
        postRxAction(rxAction, httpObservable, canShowLoading = true, canRetry = true)
    }
}
