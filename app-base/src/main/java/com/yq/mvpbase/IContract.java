package com.yq.mvpbase;

public interface IContract {

    interface IPresenter {
    }

    interface IView extends ILoadingHandler {
        void onNetWorkError();
    }

}
