package com.kvin.toolkit.utils.http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kvin on 2016/1/12.
 */
public class StringUtils {
    //Json tools
    private static JSONObject object;

    //generate getRequest params
    public static String generateGetParams(HashMap<String, String> params) {
        String str = "?";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            str += entry.getKey() + "=" + entry.getValue() + "&";
        }
        return str.substring(0, str.length() - 1);
    }

    // judge whether a string can be convert to JSONObject by using exception
    public static boolean isJSONObject(String src) {
        try {
            object = new JSONObject(src);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String toStringFromStream(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String t = "";
        String out = "";
        try {
            while ((t = br.readLine()) != null) {
                out += t;
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static JSONObject getJSONObject() {
        return object;
    }
}
