package com.kvin.toolkit.utils.load;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.kvin.toolkit.constants.Load;
import com.kvin.toolkit.utils.LogUtils;

public class DownloadThread extends Thread {
    private long start;
    private long readSize;
    private URL url;
    private File output;
    private Handler mHandler;
    private Message msg;


    @Override
    public void run() {
        super.run();
        LogUtils.v("-------- download start");
        try {
            //long cp = 0;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setAllowUserInteraction(true);
            conn.setRequestProperty("Range", "bytes=" + start + "-" + (start
                    + readSize));
            int status = conn.getResponseCode();
            LogUtils.v("status=" + status);
            if (conn.getResponseCode() == 206) {//partial content
                BufferedInputStream bis = new BufferedInputStream(
                        conn.getInputStream());
                RandomAccessFile rafOut = new RandomAccessFile(output, "rw");
                // bis.mark((int) start);
                // bis.skip(start);
                rafOut.seek(start);
                int len = 0;
                byte[] b = new byte[DownLoadUtils.READ_SIZE];
                while ((len = bis.read(b)) != -1) {
                    rafOut.write(b, 0, len);
                    msg = Message.obtain();
                    msg.what = Load.PROGRESS;
                    msg.arg1 = len;
                    mHandler.sendMessage(msg);//send loaded length
                    LogUtils.v("loading...");
                }
                rafOut.close();
                bis.close();
                conn.disconnect();
                mHandler.sendEmptyMessage(Load.Status.THREAD_FINISH);
                LogUtils.v(getName() + ":" + start + "----finish");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public DownloadThread(long start, long end, URL url,
                          File output, Handler mHandler) {
        super();
        this.start = start;
        this.readSize = end;
        this.url = url;
        this.output = output;
        this.mHandler = mHandler;
    }
}
