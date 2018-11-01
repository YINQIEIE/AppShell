package com.jdhr.egarten.dialog;

import android.content.Context;

import com.jdhr.egarten.R;


/**
 * 加载提示 dialog
 */

public class LoadingDialog extends BaseDialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void init() {

    }

}
