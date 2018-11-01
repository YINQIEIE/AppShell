package com.yq.mvpbase.factory;

import com.yq.mvpbase.BasePresenter;
import com.yq.mvpbase.IContract;

public interface PresenterFactory<V extends IContract.IView, P extends BasePresenter<V>> {

    P createPresenter();

}
