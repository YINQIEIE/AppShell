package com.yq.mvpbase.factory;

import com.yq.mvpbase.BasePresenter;
import com.yq.mvpbase.IContract;

public interface IPresenterProxy<V extends IContract.IView, P extends BasePresenter<V>> {

    void setPresentFactory(PresenterFactory<V, P> factory);

    PresenterFactory getPresentFactory();

    P getPresenter();
}
