package com.kvin.toolkit.utils;

import java.util.List;

/**
 * Created by Kvin on 2017/1/19.
 */
public class ListUtils {

    /**
     * judge whether a list is empty
     */
    public static boolean isEmpty(List<?> list) {
        if (list == null || list.size() == 0) return true;
        return false;
    }

}