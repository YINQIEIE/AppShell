package com.yq.mvpbase.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * 今日头条适配方案
 */
public class UIAdapterUtil {

    private static float sNonCompactDensity, sNonComactScaeledDensity;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = appDisplayMetrics.scaledDensity = targetDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics atyDisplayMetrics = activity.getResources().getDisplayMetrics();
        atyDisplayMetrics.density = appDisplayMetrics.scaledDensity = targetDensity;
        atyDisplayMetrics.densityDpi = targetDensityDpi;
    }

    public static void setCustomDensity1(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNonCompactDensity == 0) {
            sNonCompactDensity = appDisplayMetrics.density;
            sNonComactScaeledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (null != newConfig && newConfig.fontScale > 0) {
                        sNonComactScaeledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity = appDisplayMetrics.widthPixels / 400;
        final float targetScaledDensity = targetDensity * (sNonComactScaeledDensity / sNonCompactDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics atyDisplayMetrics = activity.getResources().getDisplayMetrics();
        atyDisplayMetrics.density = targetDensity;
        atyDisplayMetrics.scaledDensity = targetScaledDensity;
        atyDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
