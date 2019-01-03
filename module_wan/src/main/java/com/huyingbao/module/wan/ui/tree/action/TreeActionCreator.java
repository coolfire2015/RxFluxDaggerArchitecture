package com.huyingbao.module.wan.ui.tree.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2018/12/26.
 */
@ActivityScope
public class TreeActionCreator extends RxActionCreator implements TreeAction {
    @Inject
    TreeActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }
}