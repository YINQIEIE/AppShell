package com.jdhr.egarten.ui;

import com.jdhr.egarten.R;
import com.jdhr.egarten.base.BaseBKMvpActivity;

public class MainActivity extends BaseBKMvpActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    @Override
    public int getTitleBarLayout() {
        return 0;
    }
}
