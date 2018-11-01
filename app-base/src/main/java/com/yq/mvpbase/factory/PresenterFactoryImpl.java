package com.yq.mvpbase.factory;

import com.yq.mvpbase.BasePresenter;
import com.yq.mvpbase.IContract;

public class PresenterFactoryImpl<V extends IContract.IView, P extends BasePresenter<V>> implements PresenterFactory<V, P> {

    private Class<P> mPresenterClass;

    private PresenterFactoryImpl(Class<P> mPresenterClass) {
        this.mPresenterClass = mPresenterClass;
    }

    public static <V extends IContract.IView, P extends BasePresenter<V>> PresenterFactoryImpl createFactory(Class viewClass) {
        CreatePresenter annotation = (CreatePresenter) viewClass.getAnnotation(CreatePresenter.class);
        Class<P> pClass = null;
        if (null != annotation && annotation instanceof CreatePresenter) {
            pClass = (Class<P>) annotation.value();
        }
        return pClass == null ? null : new PresenterFactoryImpl(pClass);
    }

    @Override
    public P createPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Create Presenter failed ! Check if the annotation is used correctly ! ");
        }
    }
}
