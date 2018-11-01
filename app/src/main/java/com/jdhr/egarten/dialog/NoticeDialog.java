package com.jdhr.egarten.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.jdhr.egarten.R;

import butterknife.BindView;

public class NoticeDialog extends BaseDialog {

    @BindView(R.id.btn_ok)
    Button btn_ok;
    private View.OnClickListener onClickListener;

    public NoticeDialog(Context context, View.OnClickListener onClickListener) {
        super(context);
        this.onClickListener = onClickListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_notice;
    }

    @Override
    protected void init() {
        if (null != onClickListener)
            btn_ok.setOnClickListener(onClickListener);
    }
}
