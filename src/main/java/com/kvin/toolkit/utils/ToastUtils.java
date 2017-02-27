package com.kvin.toolkit.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SC-002 on 2016/12/16.
 */
public class ToastUtils {
    /**
     * simple toast
     */
    public static void simpleToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * when needing to toast much
     */
    private static Context mContext;
    private static ToastUtils mToastUtils;

    public static ToastUtils init(Context context) {
        mContext = context;
        if (mToastUtils == null)
            mToastUtils = new ToastUtils();
        return mToastUtils;
    }

    private ToastUtils() {
    }

    /**
     * after your initializing it
     *
     * @param msg
     */
    public void simpleToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
