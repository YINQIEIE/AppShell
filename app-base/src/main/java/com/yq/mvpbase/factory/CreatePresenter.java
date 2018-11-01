package com.yq.mvpbase.factory;

import android.support.annotation.NonNull;

import com.yq.mvpbase.BasePresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    @NonNull
    Class<? extends BasePresenter> value();
}
