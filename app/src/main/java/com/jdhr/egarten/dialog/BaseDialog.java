package com.jdhr.egarten.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.WindowManager;

import com.jdhr.egarten.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 测评项显示 dialog
 */
public abstract class BaseDialog extends AlertDialog {

    private Unbinder unbinder;

    public BaseDialog(Context context) {
        super(context, R.style.style_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setSize();
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void setSize() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init();

    @Override
    protected void onStart() {
        super.onStart();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbinder.unbind();
    }

}
