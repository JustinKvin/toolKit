package com.kvin.toolkit.utils.http;

/**
 * Created by SC-002 on 2016/12/13.
 */
public class WebUtils {
    private static final String WEB_REGEX = "[a-zA-z]+://\\S*";

    //check url
    public static boolean invalid(String url) {
        if (!url.matches(WEB_REGEX)) return true;
        return false;
    }
}
