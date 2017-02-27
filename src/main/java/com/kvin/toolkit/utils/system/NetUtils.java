package com.kvin.toolkit.utils.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SC-002 on 2016/12/23.
 */
public class NetUtils {
    /**
     * check whether wifi is connected
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (wifiState == NetworkInfo.State.CONNECTED) return true;
        return false;
    }

    /**
     * check whether mobile net is connected
     */
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (mobileState == NetworkInfo.State.CONNECTED) return true;
        return false;
    }

    /**
     * check whether net is connected
     */
    public static boolean isNetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState == NetworkInfo.State.CONNECTED || mobileState == NetworkInfo.State.CONNECTED)
            return true;
        return false;
    }


}
