package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.kvin.toolkit.R;

/**
 * Created by JGW-android on 2016/4/15.
 */
public class FrameLoadingLayout extends LoadingLayout {
    private AnimationDrawable mFrame;

    public FrameLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        mHeaderText.setVisibility(GONE);
        mSubHeaderText.setVisibility(GONE);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mIconContainer.setLayoutParams(params);
        mIconContainer.setGravity(Gravity.CENTER);
        mHeaderImage.setBackgroundResource(R.drawable.loading_anim);
        mFrame = (AnimationDrawable) mHeaderImage.getBackground();
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.anim_default_transparent;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {

    }

    @Override
    protected void pullToRefreshImpl() {

    }

    //is refreshing
    @Override
    protected void refreshingImpl() {
        mFrame.start();
    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    //reset anim
    @Override
    protected void resetImpl() {
        mHeaderImage.setVisibility(VISIBLE);
        mHeaderImage.clearAnimation();
    }
}
