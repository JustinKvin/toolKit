package com.kvin.toolkit.utils.system;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Kvin on 2016/12/27.
 */
public class ServiceUtils {
    //check whether the object service is working
    public static boolean isServiceWork(Context mContext, String serviceSimpleName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getShortClassName().toString();
            mName = mName.substring(mName.lastIndexOf(".") + 1, mName.length());
            if (mName.equals(serviceSimpleName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
