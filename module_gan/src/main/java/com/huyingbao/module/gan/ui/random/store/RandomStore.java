package com.huyingbao.module.gan.ui.random.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.module.gan.action.GanConstants;
import com.huyingbao.module.gan.action.GanResponse;
import com.huyingbao.module.gan.ui.random.action.RandomAction;
import com.huyingbao.module.gan.ui.random.model.Product;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * 如果OS销毁app释放资源，用户数据不会丢失；
 * 当网络很差或者断网的时候app可以继续工作。
 * <p>
 * ViewModel的目的是获取并保存Activity或Fragment所必需的信息
 * Activity或Fragment应该能够观察到ViewModel中的变化
 * <p>
 * ViewModel对象在获取ViewModel时被限定为传递给ViewModelProvider的生命周期。
 * ViewModel保留在内存中，直到Activity销毁或Fragment分离之前。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class RandomStore extends RxActivityStore {
    private MutableLiveData<List<Product>> mProductListLiveData = new MutableLiveData<>();
    private int mNextRequestPage = 1;//列表页数
    private String mCategory;

    @Inject
    RandomStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        mNextRequestPage = 1;
        mCategory = null;
        mProductListLiveData.setValue(null);
    }

    /**
     * 接收RxAction
     * 处理RxAction携带的数据
     * 发送RxChange通知RxView
     *
     * @param rxAction
     */
    @Override
    @Subscribe()
    public void onRxAction(RxAction rxAction) {
        switch (rxAction.getTag()) {
            case RandomAction.GET_PRODUCT_LIST:
                GanResponse<Product> response = rxAction.getResponse();
                if (mProductListLiveData.getValue() == null) {
                    mProductListLiveData.setValue(response.getResults());
                } else {
                    mProductListLiveData.getValue().addAll(response.getResults());
                    mProductListLiveData.setValue(mProductListLiveData.getValue());
                }
                mNextRequestPage++;
                break;
            case RandomAction.TO_SHOW_DATA:
                onCleared();//跳转页面，先清除旧数据
                mCategory = rxAction.get(GanConstants.Key.CATEGORY);
                postChange(RxChange.newInstance(rxAction));
                break;
        }
    }

    public MutableLiveData<List<Product>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String stringExtra) {
        mCategory = stringExtra;
    }

    public int getNextRequestPage() {
        return mNextRequestPage;
    }

    public void setNextRequestPage(int nextRequestPage) {
        mNextRequestPage = nextRequestPage;
    }
}
