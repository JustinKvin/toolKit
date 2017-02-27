package com.kvin.toolkit.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Kvin on 2017/2/9.
 */
public abstract class BasicPagerAdapter extends PagerAdapter {
    private List<View> views;
    private OnPagerClickListener mOnPagerClickListener;


    public BasicPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = views.get(position);
        container.addView(v);
        v.setTag(position);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnPagerClickListener != null)
                    mOnPagerClickListener.onPagerClick(view, (Integer) view.getTag());
            }
        });
        return views == null ? super.instantiateItem(container, position) : views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    /**
     *
     * @param mOnPagerClickListener
     */
    public void setOnPagerClickListener(OnPagerClickListener mOnPagerClickListener) {
        this.mOnPagerClickListener = mOnPagerClickListener;
    }

    public interface OnPagerClickListener {
        void onPagerClick(View view, int position);
    }
}
