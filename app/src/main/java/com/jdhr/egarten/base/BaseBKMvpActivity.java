package com.jdhr.egarten.base;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jdhr.egarten.dialog.LoadingDialog;
import com.jdhr.egarten.dialog.NoticeDialog;
import com.jdhr.egarten.R;
import com.yq.mvpbase.BaseMVPActivity;
import com.yq.mvpbase.BasePresenter;
import com.yq.mvpbase.ILoadingHandler;
import com.yq.mvpbase.util.SPUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseBKMvpActivity<P extends BasePresenter> extends BaseMVPActivity<P> implements ILoadingHandler {

    private Unbinder unbinder;
    protected Toolbar toolbar;
    private NoticeDialog reLoginDialog;
    private LoadingDialog loadingDialog;
    private ViewStub mStubView;
    protected View mStubViewInflated;
    protected TextView tv_msg;//提示信息
    protected Button btn_reload;//点击重试按钮
    private FrameLayout fl_titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        init();
    }

    protected abstract void init();

    @Override
    public void setContentView(int layoutResID) {
        View rootView = baseInit(layoutResID);
        setContentView(rootView);
    }

    @NonNull
    private View baseInit(int layoutResID) {
        View rootView = getLayoutInflater().inflate(R.layout.activity_layout_base, null);
        FrameLayout fl_content = rootView.findViewById(R.id.fl_content);

        //初始化t标题栏部分
        fl_titleBar = rootView.findViewById(R.id.fl_titleBar);
        if (getTitleBarLayout() != 0) {//有标题栏显示
            getLayoutInflater().inflate(getTitleBarLayout(), fl_titleBar, true);
            toolbar = fl_titleBar.findViewById(R.id.toolbar);
            initToolbar();
        } else
            setTitleBarGone();//无标题栏显示
        //初始化页面内容部分
        View contentView = getLayoutInflater().inflate(layoutResID, null);
        fl_content.addView(contentView);

        //初始化 mStubView 部分
        mStubView = rootView.findViewById(R.id.viewStub);
        if (getStubViewLayout() != 0)
            getLayoutInflater().inflate(getStubViewLayout(), fl_content, true);
        return rootView;
    }

    /**
     * 设置标题栏不可见，如启动页引导页等需全屏显示页面
     */
    private void setTitleBarGone() {
//        ((ViewGroup) rootView).removeView(fl_titleBar);//没有 titleBar 的页面移除布局
        fl_titleBar.setVisibility(View.GONE);
//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_titleBar.getLayoutParams();
//        layoutParams.height = 0;
//        fl_titleBar.setLayoutParams(layoutParams);
    }

    protected void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initStubView() {
        if (null == mStubView)
            mStubView = findViewById(R.id.viewStub);
        if (null == mStubViewInflated) {
            mStubViewInflated = mStubView.inflate();
            tv_msg = findViewById(R.id.tv_msg);
            btn_reload = findViewById(R.id.btn_reload);
            btn_reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload();
                }
            });
        }
    }

    /**
     * 没有数据或者网络问题点击重新加载数据
     * 子类中实现
     */
    protected void reload() {
    }

    @LayoutRes
    public int getTitleBarLayout() {
        return R.layout.layout_toolbar;
    }

    /**
     * 可以设置需要的 StubView
     *
     * @return layoutID
     */
    protected int getStubViewLayout() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (reLoginDialog != null && reLoginDialog.isShowing()) {
            reLoginDialog.dismiss();
            reLoginDialog = null;
        }
    }

    /**
     * 由于后台 token 发生变化，登录过期或失效，重新登录
     */
    public void reLogin() {
        clearToken();
        if (null == reLoginDialog) {
            reLoginDialog = new NoticeDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            reLoginDialog.setCanceledOnTouchOutside(false);
            reLoginDialog.setCancelable(false);
        }
        if (!reLoginDialog.isShowing())
            reLoginDialog.show();
    }

    protected void dismissReloginDialog() {
        if (null != reLoginDialog && reLoginDialog.isShowing())
            reLoginDialog.dismiss();
    }

    public String getToken() {
        return SPUtil.getStringValByKey(this, "token");
    }

    /**
     * 登录失效后先将token设置为空
     */
    public void clearToken() {
        SPUtil.saveString(this, "token", "");
    }

    public void showLoadingDialog() {
        createLoadingDialog();
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (null != loadingDialog && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    protected void createLoadingDialog() {
        if (null == loadingDialog)
            loadingDialog = new LoadingDialog(this);
    }

    @Override
    public void showLoadingView() {
        showLoadingDialog();
    }

    @Override
    public void dismissLoadingView() {
        dismissLoadingDialog();
    }

    public static final int STATE_NET_ERROR = 0;
    public static final int STATE_NO_DATA = 1;
    public static final int STATE_GONE = 2;

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({STATE_NET_ERROR, STATE_NO_DATA, STATE_GONE})
    public @interface StubViewState {
        int value() default 0;
    }

    protected void setStubViewState(@StubViewState int state) {
        if (state == STATE_NET_ERROR) {
            initStubView();
            mStubView.setVisibility(View.VISIBLE);
            tv_msg.setText("网络错误");
        } else if (state == STATE_NO_DATA) {
            initStubView();
            mStubView.setVisibility(View.VISIBLE);
            tv_msg.setText("暂无数据");
        } else if (state == STATE_GONE) {
            mStubView.setVisibility(View.GONE);
        }
    }

    protected void onNetWorkError() {
        setStubViewState(STATE_NET_ERROR);
    }

}
