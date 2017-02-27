package com.kvin.toolkit.utils.load;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.kvin.toolkit.constants.Load;
import com.kvin.toolkit.utils.OnReceiveListener;


public class LoadService extends Service {
    private int finishNum;
    private int threadNum;
    private CommandReceiver mReceiver;
    private String fileId;
    private String fileUrl;
    private long loadedSize;
    private long fileSize;

    private Intent mIntent;

    public LoadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mReceiver = new CommandReceiver();
        registerReceiver(mReceiver, new IntentFilter(Load.LOAD_COMMAND_ACTION));
        mReceiver.setOnReceiveListener(mOnReceiveListener);//use for receiving commands

        schemaLoad(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * schema load
     *
     * @param intent id use for generating file name and url for downloading file
     */
    private void schemaLoad(Intent intent) {
        fileId = intent.getStringExtra(Load.FILE_ID);
        fileUrl = intent.getStringExtra(Load.FILE_URL);
        DownLoadUtils.init(fileId, fileUrl, mHandler);

        sendReport(Load.Status.LOAD_START, fileId);
    }

    /**
     * send report
     *
     * @param status
     * @param fileId
     */
    private void sendReport(int status, String fileId) {
        mIntent = new Intent().setAction(Load.LOAD_REPORT_ACTION);
        mIntent.putExtra(Load.LOAD_STATUS, status);
        mIntent.putExtra(Load.FILE_ID, fileId);
        sendBroadcast(mIntent);
    }

    /**
     * handle file download
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Load.PROGRESS:
                    loadedSize += msg.arg1;//you can send total progress for progressbar
                    break;
                case Load.Status.LOAD_FINISH:
                    // schema net file
                    sendReport(Load.Status.LOAD_FINISH, fileId);
                    break;
                case Load.FILE_SIZE:
                    fileSize = msg.arg1;//file size
                    threadNum = msg.arg2;//opened threads
                    break;
                case Load.Status.THREAD_FINISH:
                    //complete
                    finishNum++;
                    if (finishNum == threadNum)
                        mHandler.sendEmptyMessage(Load.Status.LOAD_FINISH);
                    break;
            }
        }
    };

    private OnReceiveListener mOnReceiveListener = new OnReceiveListener() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(Load.LOAD_STATUS, 0);
            switch (status) {
                case Load.Status.ADD_FILE:
                    schemaLoad(intent);
                    break;
                case Load.Status.LOAD_CLOSE:
                    stopSelf();
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
