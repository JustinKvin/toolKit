package com.kvin.toolkit.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.kvin.toolkit.R;


/**
 * Created by Kvin on 2017/1/20.
 */
public class PopupUtils {

    private static final int DEFAULT_RES = -1;
    private static View v;
    private static PopupWindow mPop;

    private PopupUtils(View contentView, Context context, @LayoutRes int res, int w, int h) {
        if (contentView != null) {
            v = contentView;
        } else if (context != null && res != DEFAULT_RES) {
            v = LayoutInflater.from(context).inflate(res, null);
        } else {
            return;
        }
        mPop = new PopupWindow(v, w, h);
        mPop.setClippingEnabled(true);
        mPop.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setOutsideTouchable(true);
        mPop.setFocusable(true);
    }

    /**
     * constructor
     */
    public static PopupUtils newInstance(Context context, @LayoutRes int res, int w, int h) {
        return new PopupUtils(null, context, res, w, h);
    }

    public static PopupUtils newInstance(View contentView, int w, int h) {
        return new PopupUtils(contentView, null, DEFAULT_RES, w, h);
    }


    /**
     * set animation style
     */
    public void setAnimationStyle(@StyleRes int animationStyle) {
        mPop.setAnimationStyle(animationStyle);
    }

    public void setAnimationStyle(AnimStyle style) {
        switch (style) {
            case LEFT_IN_LEFT_OUT:
                mPop.setAnimationStyle(R.style.PopupWindow_Left_in_left_out);
                break;
            case RIGHT_IN_RIGHT_OUT:
                mPop.setAnimationStyle(R.style.PopupWindow_Right_in_right_out);
                break;
            case TOP_IN_TOP_OUT:
                mPop.setAnimationStyle(R.style.PopupWindow_Top_in_top_out);
                break;
            case BOTTOM_IN_BOTTOM_OUT:
                mPop.setAnimationStyle(R.style.PopupWindow_Bottom_in_bottom_out);
                break;
        }
    }

    /**
     * set  location
     */
    public void show(View anchor, int gravity) {
        show(anchor, gravity, 0, 0);
    }

    public void show(View parent, int gravity, int x, int y) {
        if (mPop != null)
            mPop.showAtLocation(parent, gravity, x, y);
    }

    public void show(View anchor) {
        show(anchor, Gravity.CENTER, 0, 0);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (mPop != null)
            mPop.showAsDropDown(anchor, xoff, yoff);
    }

    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
    }


    /**
     * dismiss
     */
    public void dismiss() {
        mPop.dismiss();
    }

    /**
     * animation style
     */
    public enum AnimStyle {
        RIGHT_IN_RIGHT_OUT(0),
        LEFT_IN_LEFT_OUT(1),
        TOP_IN_TOP_OUT(2),
        BOTTOM_IN_BOTTOM_OUT(3);

        private int value;

        AnimStyle(int value) {
            this.value = value;
        }
    }
}