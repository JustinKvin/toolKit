package com.kvin.toolkit.utils.system;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Kvin on 2017/1/23.
 */
public class KeyboardUtils {

    /**
     * hide or show soft keyboard
     */
    public static void closeKeyboard(Context context) {
        Activity act = (Activity) context;
        InputMethodManager input = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
