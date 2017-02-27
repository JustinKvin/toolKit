package com.kvin.toolkit.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kvin on 2016/12/27.
 */
public class JumpUtils {
    public static final boolean DEFAULT_STATUS = false;

    /**
     * simple jump
     */
    public static void simpleJump(Activity from, Class<?> to) {
        if (to == null) return;
        simpleJump(from, to, null, DEFAULT_STATUS);
    }

    /**
     * simple jump
     */
    public static void simpleJump(Activity from, Intent intent) {
        if (intent == null) return;
        simpleJump(from, null, intent, DEFAULT_STATUS);
    }

    /**
     * simple jump
     */
    public static void simpleJump(Activity from, Class<?> to, Intent intent, boolean isFinish) {
        if (from == null) return;
        if (to != null) {
            Intent i = new Intent(from, to);
            from.startActivity(i);
        } else if (intent != null) {
            from.startActivity(intent);
        }
        //res finish
        if (isFinish) {
            from.finish();
        }
    }

    /**
     * jump with bundle
     */
    public static void jumpWithBundle(Activity from, Class<?> to, Bundle data) {
        Intent intent = new Intent(from, to);
        intent.putExtras(data);
        simpleJump(from, intent);
    }

    /**
     * jump with params
     */
    public static void jumpWithParams(Activity from, Class<?> to, HashMap<String, String> params) {
        jumpWithParams(from, to, params, DEFAULT_STATUS);
    }

    /**
     * jump with params
     */
    public static void jumpWithParams(Activity from, Class<?> to, HashMap<String, String> params, boolean isFinish) {
        if (from == null || to == null) return;
        Intent intent = new Intent(from, to);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        from.startActivity(intent);

        if (isFinish) {
            from.finish();
        }
    }

    /**
     * start activity for result
     */
    public static void jumpWithResult(Activity from, Intent intent, int requestCode) {
        if (from == null || intent == null) return;
        from.startActivityForResult(intent, requestCode);
    }

    public static void jumpWithResult(Activity from, Class<?> to, int requestCode) {
        if (from == null || to == null) return;
        from.startActivityForResult(new Intent(from, to), requestCode);
    }
}
