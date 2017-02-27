package com.kvin.toolkit.utils;

/**
 * Created by SC-002 on 2016/12/19.
 */
public class UrlUtils {

    /**
     * check whether an url is accessible
     * @param urlPath
     * @return
     */
    public static boolean canAccess(String urlPath) {
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
//                try {
//                    URL url = new URL(urlPath);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                        return true;
//                    }
//                } catch (MalformedURLException e) {
//                    return false;
//                } catch (IOException e) {
//                    return false;
//                }
            }
        });
        return false;
    }
}
