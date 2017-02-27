package com.kvin.toolkit.utils.system;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by Kvin on 2017/2/15.
 */
public class PermissionUtils {
    /**
     * check permission
     * PackageManager.PERMISSION_DENIED    failure
     * PackageManager.PERMISSION_GRANTED   ok
     */
    public static boolean checkPermission(Context context, String permission) {
        int resCode = ContextCompat.checkSelfPermission(context, permission);

        return resCode == PackageManager.PERMISSION_GRANTED;
    }
}
