package com.yq.mvpbase.factory;

import com.yq.mvpbase.BasePresenter;
import com.yq.mvpbase.IContract;

public class PresenterProxy<V extends IContract.IView, P extends BasePresenter<V>> implements IPresenterProxy<V, P> {

    private PresenterFactory<V, P> presenterFactory;
    private P mPresenter;

    public PresenterProxy(PresenterFactory<V, P> presenterFactory) {
        this.presenterFactory = presenterFactory;
    }

    @Override
    public void setPresentFactory(PresenterFactory<V, P> factory) {
        if (mPresenter != null) {
            throw new IllegalStateException("这个方法只能在 getPresenter() 之前调用，如果Presenter已经创建则不能再修改");
        }
        this.presenterFactory = factory;
    }

    @Override
    public PresenterFactory getPresentFactory() {
        return presenterFactory;
    }

    @Override
    public P getPresenter() {
        if (presenterFactory != null) {
            if (null == mPresenter)
                mPresenter = presenterFactory.createPresenter();
        }
        return mPresenter;
    }
}
