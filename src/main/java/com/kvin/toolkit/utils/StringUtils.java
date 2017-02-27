package com.kvin.toolkit.utils;

import android.graphics.Paint;
import android.graphics.Rect;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Kvin on 2017/1/6.
 */
public class StringUtils {
    public static final SimpleDateFormat SDF=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String REGEX_NUMBER = "\\d+";

    public static String combineB(String prefix, String suffix) {
        return prefix + "_" + suffix;
    }

    public static String combineM(String prefix, String suffix) {
        return prefix + "_" + suffix;
    }


    /**
     * check whether a string can be parsed into integer
     */
    public static boolean isNumber(String src) {
        if (src.matches(REGEX_NUMBER)) return true;
        return false;
    }

    /**
     * check whether a string is empty
     */
    public static boolean isEmpty(String src) {
        if (src == null || src.equals("")) return true;
        return false;
    }

    /**
     * parse into time
     */
    public static String parseTime(int sm) {
        sm /= 1000;
        int min = sm / 60;
        int sec = sm % 60;

        return String.format("%02d:%02d", min, sec);
    }

    /**
     * get file type
     */
    public static String getFileName(String input) {
        if (isEmpty(input)) return null;
        return input.substring(input.lastIndexOf("."), input.length());
    }

    /**
     * remove prefix
     */
    public static String removePrefix(String input) {
        return removePrefix(input, ".");
    }

    public static String removePrefix(String input, String symbol) {
        return input.substring(input.indexOf(symbol) + 1, input.length());
    }

    /**
     * remove suffix
     */
    public static String removeSuffix(String input) {
        return removeSuffix(input, ".");
    }

    public static String removeSuffix(String input, String symbol) {
        return input.substring(0, input.lastIndexOf(symbol));
    }


    /**
     * list to string
     *
     * @param list   data resource
     * @param symbol a divide symbol
     */
    public static String intoString(List<String> list, String symbol) {
        String output = "";
        if (ListUtils.isEmpty(list)) return output;
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) return output + list.get(i);
            output += list.get(i) + symbol;
        }

        return output;
    }

    /**
     * a string array to string
     *
     * @param src    data resource
     * @param symbol a divide symbol
     */
    public static String intoString(String[] src, String symbol) {
        String output = "";
        if (isEmpty(src)) return output;
        for (int i = 0; i < src.length; i++) {
            if (i == src.length - 1) return output + src[i];
            output += src[i] + symbol;
        }
        return output;
    }

    /**
     * judge whether a string array is empty
     */
    public static boolean isEmpty(String[] srs) {
        if (srs == null || srs.length <= 0) return true;
        return false;
    }

    /**
     * get suffix
     */
    public static String getMediaSuffix(String s) {
        return s.substring(s.lastIndexOf(".") + 1, s.length());
    }

    /**
     * text width
     */
    public static float getTextWidth(String txt, Paint txtPaint) {
        Rect rect = new Rect();
        txtPaint.getTextBounds(txt, 0, txt.length(), rect);
        return rect.width();
    }

    /**
     * text height
     *
     * @return
     */
    public static float getTextHeight(Paint txtPaint) {
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        return Math.abs(fontMetrics.top);
    }
}
