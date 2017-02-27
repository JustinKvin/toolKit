package com.kvin.toolkit.adapter;

import android.view.View;

/**
 * Created by Kvin on 2017/2/21.
 */
public interface OnChildClickListener {
    /**
     * use for setting click event for children views
     * @param v
     * @param position
     */
    void onClick(View v, int position);
}
