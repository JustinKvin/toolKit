package com.kvin.toolkit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kvin.toolkit.utils.comparator.NumComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by SC-002 on 2016/12/14.
 */
public class PersistentUtils {
    private static final String DEFAULT_STRING = "";
    private SharedPreferences mPreferences;
    private static PersistentUtils mUtils;

    /**
     * file name
     */
    public static class XmlFile {
        public static final String USER_INFO = "user_info";
        public static final String PLAY_RECORD = "play_record";
        public static final String DEFAULT_FILE = "default_file";
    }

    /**
     * init persistent utils
     *
     * @param context
     */
    public static PersistentUtils getInstance(Context context) {
        if (mUtils == null) mUtils = new PersistentUtils(context);
        return mUtils;
    }

    /**
     * provide a full name
     */
    public static PersistentUtils getInstance(Context context, String fileName) {
        if (mUtils == null)
            mUtils = new PersistentUtils(context, fileName);
        return mUtils;
    }

    /**
     * use when needing to handle two or more different xml
     */
    public static PersistentUtils newInstance(Context context, String fileName) {
        mUtils = new PersistentUtils(context, fileName);
        return mUtils;
    }

    private PersistentUtils(Context context) {
        mPreferences = context.getSharedPreferences(XmlFile.DEFAULT_FILE, Context.MODE_PRIVATE);
    }

    private PersistentUtils(Context context, String fileName) {
        mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * generate file name
     */
    private static String generateFileName(String suffix) {
        return XmlFile.DEFAULT_FILE + "-" + suffix;
    }

    /**
     * write string
     */
    public void writeString(String key, String value) {
        mPreferences.edit().putString(key, value).commit();
    }

    public String readString(String key) {
        return mPreferences.getString(key, DEFAULT_STRING);
    }

    /**
     * Write boolean data
     */
    public void writeBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).commit();
    }

    public boolean readBoolean(String key) {
        return mPreferences.getBoolean(key, true);
    }

    /**
     * write int data
     */
    public void writeInt(String key, int value) {
        mPreferences.edit().putInt(key, value).commit();
    }

    public int readInt(String key) {
        return mPreferences.getInt(key, -2);
    }

    /**
     * write long data
     */
    public void writeLong(String key, long value) {
        mPreferences.edit().putLong(key, value).commit();
    }

    public long readLong(String key) {
        return mPreferences.getLong(key, System.currentTimeMillis());
    }

    /**
     * write a string array
     */
    public void writeParams(HashMap<String, String> params) {
        if (params == null) return;
        for (Map.Entry<String, String> item : params.entrySet()) {
            mPreferences.edit().putString(item.getKey(), item.getValue()).apply();
        }
    }

    /**
     * write a list
     */
    public void writeList(String key, ArrayList<String> data) {
        Set<String> set = new LinkedHashSet<>();//store in nature order
        for (int i = 0; i < data.size(); i++) {
            set.add(i + "." + data.get(i));//attention: set will remove the same samples,so i do in this way
        }
        mPreferences.edit().putStringSet(key, set).apply();//apply() can handle in background
    }

    /**
     * read a list in nature order
     */
    public ArrayList<String> readList(String key) {
        Set<String> set = mPreferences.getStringSet(key, null);
        if (set == null) return null;
        ArrayList<String> data = new ArrayList<>();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String output = iterator.next();
            data.add(output);
        }
        //sort
        Collections.sort(data, new NumComparator());
        //subString
        for (int i = 0; i < data.size(); i++) {
            String item = data.get(i);
            data.set(i, item.substring(item.indexOf(".") + 1, item.length()));
        }

        return data;
    }

    /**
     * clear
     */
    public void clear() {
        mPreferences.edit().clear().commit();
        LogUtils.v("---clear successfully");
    }
}
