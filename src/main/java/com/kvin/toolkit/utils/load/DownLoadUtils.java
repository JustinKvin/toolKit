package com.kvin.toolkit.utils.load;

import android.os.Handler;
import android.os.Message;

import com.kvin.toolkit.constants.Load;
import com.kvin.toolkit.utils.*;
import com.kvin.toolkit.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kvin on 2017/2/20.
 */
public class DownLoadUtils {
    public static final int READ_SIZE = 256 * 1024;// 256k
    public static final int DOWNLOAD_LIMIT = 5 * 4 * READ_SIZE;//5M
    private static File outFile;
    private static Handler mHandler;

    /**
     * init
     */
    public static void init(String fileId, String fileUrl, Handler handler) {
        if (StringUtils.isEmpty(fileId) || StringUtils.isEmpty(fileUrl)) {
            try {
                throw new Exception("storing path is null or remote url is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        mHandler = handler;
        outFile = FileUtils.newFile(FileUtils.getMediaPath(), fileId + fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length()));
        ThreadPoolUtils.execute(getTask(fileUrl));
    }

    /**
     * download
     *
     * @param fileUrl
     * @return
     */
    private static Runnable getTask(final String fileUrl) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(fileUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn.getResponseCode() == 200) {
                        int length = conn.getContentLength();
                        LogUtils.v("--length=" + length);
                        if (length == -1)
                            return;
                        double cpd = (double) length / DOWNLOAD_LIMIT;
                        int cpi = (int) (length / DOWNLOAD_LIMIT);
                        int count = cpd > cpi ? cpi + 1 : cpi;
                        Message msg = Message.obtain();
                        msg.what = Load.FILE_SIZE;
                        msg.arg1 = length;
                        msg.arg2=count;
                        mHandler.sendMessage(msg);

                        LogUtils.v("---count=" + count);
                        for (int i = 0; i < count; i++) {//calculate num of threads
                            new DownloadThread(i * DOWNLOAD_LIMIT, DOWNLOAD_LIMIT, url, outFile, mHandler).start();
                        }
                        conn.disconnect();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    }
}
