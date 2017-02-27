package com.kvin.toolkit.utils.comparator;

import com.kvin.toolkit.utils.StringUtils;

import java.util.Comparator;

/**
 * Created by Kvin on 2017/2/13.
 */
public class NumComparator implements Comparator<String> {
    @Override
    public int compare(String s, String t1) {
        String prefixL = s.substring(0, s.indexOf("."));
        String prefixR = t1.substring(0, t1.indexOf("."));
        if (StringUtils.isNumber(prefixL) && StringUtils.isNumber(prefixR)) {
            return Integer.parseInt(prefixL) - Integer.parseInt(prefixR);
        }
        return s.compareTo(t1);
    }
}
