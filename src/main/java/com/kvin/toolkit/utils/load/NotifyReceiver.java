package com.kvin.toolkit.utils.load;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kvin.toolkit.utils.OnReceiveListener;

/**
 * Created by Kvin on 2017/2/20.
 */
public class NotifyReceiver extends BroadcastReceiver {
    private OnReceiveListener mOnReceiveListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mOnReceiveListener != null)
            mOnReceiveListener.onReceive(context, intent);
    }

    /**
     * on Receiver
     *
     * @param mOnReceiveListener
     */
    public void setOnReceiveListener(OnReceiveListener mOnReceiveListener) {
        this.mOnReceiveListener = mOnReceiveListener;
    }
}
