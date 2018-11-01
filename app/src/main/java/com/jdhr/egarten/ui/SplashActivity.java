package com.jdhr.egarten.ui;

import android.content.Intent;
import android.os.Handler;

import com.jdhr.egarten.R;
import com.jdhr.egarten.base.BaseBKMvpActivity;


public class SplashActivity extends BaseBKMvpActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        //安装完之后直接打开出会有问题
        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        new Handler().postDelayed(() -> {
            startNewActivity(MainActivity.class);
            finish();
        }, 1000);
    }

    /**
     * 全屏显示，不需要标题栏
     *
     * @return 0
     */
    @Override
    public int getTitleBarLayout() {
        return 0;
    }

    /**
     * 由于调用 startActivity 后直接调用了 finish
     * 会导致转场动画被覆盖，所以此处重写 finish 的
     * 动画转场为空
     */
    @Override
    protected void setFinishTransaction() {

    }

}
