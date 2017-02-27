package com.kvin.toolkit.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.kvin.toolkit.R;

/**
 * Created by Kvin on 2017/1/5.
 */
public class DialogUtils extends AlertDialog {
    private static final int THEME_DEFAULT = 0;
    private static final int RES_DEFAULT = -1;
    private static DialogUtils mUtils;
    private static View mContentView;

    private DialogUtils(Context context) {
        super(context);
    }

    private DialogUtils(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogUtils(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setWindowAnimations(R.style.DialogUtilsLoadingAnim);//set loading animation

//        getWindow().setNavigationBarColor(Color.parseColor("#00000000"));
        //set dialog size
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        params.width = (int) (metrics.widthPixels * 0.5);
//        params.height = (int) (metrics.heightPixels * 0.2);
//        getWindow().setAttributes(params);
        if (mContentView != null)
            setContentView(mContentView);
    }

    /**
     * get instance
     */
    public static DialogUtils getInstance(Context context, @LayoutRes int res, View contentView, int themeResId) {
        if (mUtils != null) return mUtils;
        //set content view
        if (res != RES_DEFAULT) {
            mContentView = View.inflate(context, res, null);
        } else if (contentView != null) {
            mContentView = contentView;
        }
        //set theme
        if (themeResId == THEME_DEFAULT) {
            mUtils = new DialogUtils(context,R.style.DialogUtils);
        } else {
            mUtils = new DialogUtils(context, themeResId);
        }
        return mUtils;
    }

    public static DialogUtils getInstance(Context context, @LayoutRes int res) {
        return getInstance(context, res, null, THEME_DEFAULT);
    }

    public static DialogUtils getInstance(Context context, View contentView) {
        return getInstance(context, RES_DEFAULT, contentView, THEME_DEFAULT);
    }

    public static DialogUtils getInstance(Context context, @LayoutRes int res, int themeResId) {
        return getInstance(context, res, null, themeResId);
    }

    public static DialogUtils getInstance(Context context, View contentView, int themeResId) {
        return getInstance(context, RES_DEFAULT, contentView, themeResId);
    }


    /**
     * set View
     */
    public void setView(@LayoutRes int res) {
        View v = getLayoutInflater().inflate(res, null);
        setView(v);
    }
}
