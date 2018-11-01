package com.yq.mvpbase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
    }

    @LayoutRes
    protected abstract int getLayoutID();

    protected void toast(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
    }

    public void startNewActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        setStartTransaction();
    }

    protected void setStartTransaction() {
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
    }


    @Override
    public void finish() {
        super.finish();
        setFinishTransaction();
    }

    protected void setFinishTransaction() {
        overridePendingTransition(R.anim.activity_left_in, R.anim.activity_right_out);
    }
}
