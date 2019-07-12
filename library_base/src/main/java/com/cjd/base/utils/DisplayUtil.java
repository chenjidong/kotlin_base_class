package com.cjd.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.cjd.base.R;

import java.lang.reflect.Field;


public class DisplayUtil {

    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;
    public static int SCREEN_STATUS_BAR_HEIGHT_PIXELS;
    public static int ACTIVITY_TOOL_BAR_HEIGHT_PIXELS;

    public static void init(Context context) {
        if (context == null) {
            return;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = dm.heightPixels;
        SCREEN_DENSITY = dm.density;
        SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PIXELS / dm.density);
        SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PIXELS / dm.density);
        ACTIVITY_TOOL_BAR_HEIGHT_PIXELS = context.getResources().getDimensionPixelOffset(R.dimen.base_toolbar_height);
        SCREEN_STATUS_BAR_HEIGHT_PIXELS = context.getResources().getDimensionPixelOffset(R.dimen.status_bar_height);
    }

    public static int dp2px(float dp) {
        final float scale = SCREEN_DENSITY;
        return (int) (dp * scale + 0.5f);
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            LogUtils.d("get status bar height fail");
            e1.printStackTrace();
            return dp2px(24);
        }
    }

    /**
     * 设置隐藏标题栏
     *
     * @param activity
     */
    public static void setNoTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void setNavigationHide(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void screenOrientation(Activity activity, boolean isPortrait) {
        if (isPortrait) {
            //竖屏设置 portrait
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //横屏设置
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
