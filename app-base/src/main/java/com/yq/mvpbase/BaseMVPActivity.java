package com.yq.mvpbase;

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.onDestroy();
    }
}
