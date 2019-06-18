package com.huyingbao.module.wan.ui.article.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.base.activity.BaseRxFragActivity;
import com.huyingbao.module.wan.app.WanAppStore;
import com.huyingbao.module.wan.ui.article.action.ArticleAction;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;
import com.huyingbao.module.wan.ui.friend.view.FriendFragment;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = "/wan/ArticleActivity")
public class ArticleActivity extends BaseRxFragActivity<ArticleStore> {
    @Inject
    WanAppStore mWanAppStore;

    @Override
    protected Fragment createFragment() {
        return ArticleListFragment.newInstance();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Subscribe(tags = {ArticleAction.TO_FRIEND}, sticky = true)
    public void toFriend(RxChange rxChange) {
        addFragmentHideExisting(FriendFragment.newInstance());
    }

    @Subscribe(tags = {ArticleAction.TO_BANNER}, sticky = true)
    public void toBanner(RxChange rxChange) {
        addFragmentHideExisting(BannerFragment.newInstance());
    }
}
