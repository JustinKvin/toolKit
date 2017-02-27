package com.kvin.toolkit.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;


import com.kvin.toolkit.constants.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SC-002 on 2016/12/20.
 */
public class FileUtils {
    public final static String IMAGE_PREFIX = "file:///";//use for read local image

    public static final String TAG = "FileUtils";
    private static final String SP_PREFIX = "play_info_";
    private static final String SP_DIRECTORY = "/data/com.saker.app.huhu/shared_prefs/";

    private static final String MEDIA_XML = ".xml";

    private static final int DAY_MILLIS = 24 * 60 * 60 * 1000;

    public static final String RECORDING_SUFFIX = "/HuHu/recording.mp3";

    public static class Media {
        public static final String JPG = ".jpg";
        public static final String PNG = ".png";
        public static final String JPEG = ".jpeg";
        public static final String MP3 = ".mp3";
        public static final String MP4 = ".mp4";
    }

    /**
     * check whether storage card is accessible
     */
    public static boolean isStorageAccessible() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) return true;
        return false;
    }

    /**
     * check whether a file exists
     */
    public static boolean isExist(File file) {
        if (file == null || !file.exists()) return false;
        return true;
    }

    /**
     * check whether an array is empty
     */
    public static boolean isEmpty(File[] files) {
        if (files == null || files.length <= 0) return true;
        return false;
    }

    /**
     * create a new file
     */
    public static File newFile(String path) {
        String dir = path.substring(0, path.lastIndexOf("/"));
        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        return newFile(dir, fileName);
    }

    public static File newFile(String dir, String fileName) {
        File preFile = new File(dir);
        if (!preFile.exists()) preFile.mkdirs();
        File resFile = new File(dir, fileName);
        if (!resFile.exists()) try {
            resFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resFile;
    }


    /**
     * clear useless sharePreference
     */
    public static void updatePlayPreference(final Context context) {
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                if (!isStorageAccessible()) {
                    LogUtils.v(TAG, "---storageCard is not accessible");
                    return;
                }
                File file = new File(getDataPath() + SP_DIRECTORY);
                LogUtils.v(TAG, "----storage=" + getDataPath());
                if (!isExist(file)) {
                    LogUtils.v(TAG, "----the file does n`t exist ");
                    return;
                }
                long currentTime = System.currentTimeMillis();
                File[] files = file.listFiles();
                for (File f : files) {
                    String fileName = f.getName().substring(0, f.getName().lastIndexOf("."));
                    if (fileName.contains(SP_PREFIX)) {
                        try {
                            long wroteTime = PersistentUtils.getInstance(context, fileName).readLong(Keys.MEDIA_TIME);
                            if (DAY_MILLIS < currentTime - wroteTime) {
                                if (f.delete()) LogUtils.v(TAG, "--delete successfully");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    LogUtils.v(TAG, "---" + f.getName());
                }
            }
        });
    }

    /**
     * storage path
     *
     * @return
     */

    public static String getRootStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * upload image path
     *
     * @return
     */
    public static String getUploadImagePath(String imageBucket, String type) {
        return getRootStoragePath() + "/" + imageBucket + "/" + (int) (Math.random() * 1000000) + type;
    }

    /**
     * download video path
     */
    public static String getVideoPath() {
        return getRootStoragePath() + "/huhu/video";
    }

    /**
     * download audio path
     */
    public static String getAudioPath() {
        return getRootStoragePath() + "/huhu/audio";
    }

    /**
     * download media path
     */
    public static String getMediaPath() {
        return getRootStoragePath() + "/huhu/media";
    }


    /**
     * data path
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * recording path
     */
    public static String getRecordingPath() {
        if (!isStorageAccessible()) return null;
        return getRootStoragePath() + RECORDING_SUFFIX;
    }

    /**
     * recording file
     */
    public static File getRecordingFile() {
        File f = null;
        if (isStorageAccessible()) {
            f = new File(getRootStoragePath() + RECORDING_SUFFIX);
        }
        return f;
    }

    /**
     * get compressed image file
     */
    public static File getFile(String imagePath, String desPath) {
        File outputFile = FileUtils.newFile(desPath);
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    /**
     * compress file
     *
     * @param input
     * @param output
     * @return
     */
    public static boolean isCompressed(File input, File output) {
        if (input == null || output == null) return false;
        try {
            FileOutputStream fos = new FileOutputStream(output);
            Bitmap bitmap = BitmapFactory.decodeFile(input.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * clear the direct files
     */
    public static boolean clear(String dir) {
        File file = new File(dir);
        if (!file.exists()) return false;
        File[] files = file.listFiles();
        for (File f : files) f.delete();
        return true;
    }
}
