package com.kvin.toolkit.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by SC-002 on 2016/12/19.
 */
public class ThreadPoolUtils {
    private static ExecutorService mService;

    /**
     * @param nThread num of thread
     */
    public static void init(int nThread) {
        if (mService == null)
            mService = Executors.newFixedThreadPool(nThread);
    }

    /**
     * execute
     */
    public static void execute(Runnable task) {
        if (mService != null)
            mService.execute(task);
    }
}
