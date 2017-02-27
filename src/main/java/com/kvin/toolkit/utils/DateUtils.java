package com.kvin.toolkit.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by SC-002 on 2016/12/21.
 */
public class DateUtils {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static long getTime(String time) {
        long result = System.currentTimeMillis();
        try {
            result = df.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
}
