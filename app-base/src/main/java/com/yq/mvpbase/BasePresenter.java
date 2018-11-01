package com.yq.mvpbase;

public abstract class BasePresenter<V extends IContract.IView & ILoadingHandler> {

    protected final String TAG = getClass().getSimpleName();

    protected V mView;

    public BasePresenter(V mView) {
        this.mView = mView;
    }

    public void onDestroy() {
        if (null != mView) mView = null;
    }

    protected void showLoadingView() {
        if (null != mView)
            mView.showLoadingView();
    }

    protected void dismissLoadingView() {
        if (null != mView)
            mView.dismissLoadingView();
    }
}