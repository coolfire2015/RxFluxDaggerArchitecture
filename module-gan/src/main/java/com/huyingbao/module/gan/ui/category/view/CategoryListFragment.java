package com.huyingbao.module.gan.ui.category.view;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.core.view.CommonFragment;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.category.adapter.CategoryAdapter;
import com.huyingbao.module.gan.ui.random.store.RandomStore;
import com.huyingbao.module.gan.ui.random.view.RandomActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 内容类型列表展示页面
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class CategoryListFragment extends CommonFragment {
    @BindView(R2.id.rv_content)
    protected RecyclerView mRvContent;

    protected List<String> mDataList = new ArrayList();
    protected BaseQuickAdapter mAdapter = new CategoryAdapter(mDataList);
    private RandomStore mStore;

    @Inject
    public CategoryListFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(RandomStore.class);
        initActionBar("首页");
        initRecyclerView();
        showData();
        mStore.setPage(0);
    }

    /**
     * 实例化RecyclerView,并设置adapter
     */
    protected void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(RandomActivity.newIntent(getActivity(), mDataList.get(position)));
            }
        });
    }

    private void showData() {
        mDataList.addAll(Arrays.asList(getResources().getStringArray(R.array.gan_category)));
        mAdapter.notifyDataSetChanged();
    }
}