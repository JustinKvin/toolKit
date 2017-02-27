package com.kvin.toolkit.utils.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

/**
 * Created by Kvin on 2017/1/22.
 */
public class CameraUtils {
    private static final String CAMERA = "Camera";

    /**
     * read album photos
     *
     * @DATA image path
     * @BUCKET_DISPLAY_NAME image parent dir
     */
    public static ArrayList<String> getAlbumImages(Context context) {
        ArrayList<String> resData = new ArrayList<>();
        ContentResolver mResolver = context.getContentResolver();
        String[] pros = new String[]{Media._ID, Media.DATA, Media.BUCKET_ID, Media.BUCKET_DISPLAY_NAME};
        Cursor mCursor = mResolver.query(Media.EXTERNAL_CONTENT_URI, pros, null, null, null);
        while (mCursor.moveToNext()) {
            String bucketName = mCursor.getString(mCursor.getColumnIndex(Media.BUCKET_DISPLAY_NAME));
            if (CAMERA.equalsIgnoreCase(bucketName)) {
                String imagePath = mCursor.getString(mCursor.getColumnIndex(Media.DATA));
                resData.add(imagePath);
            }
        }
        return resData;
    }

    /**
     * disPlay
     * compress
     */
    public static void display(String localImage, ImageView view) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(localImage);
        boolean isCompressed = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);

        if (isCompressed) {
            byte[] bitData = os.toByteArray();
            Bitmap resBitmap = BitmapFactory.decodeByteArray(bitData, 0, bitData.length);
            view.setImageBitmap(resBitmap);
        }
        bitmap.recycle();
    }

    /**
     * scale compress
     */
    public Bitmap getBitmap(String path, int maxW, int maxH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//use for measuring width or height
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;//after measured
        int scale = 1;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        while (w / scale > maxW || h / scale > maxH) {
            scale *= 2;
        }
        bitmap.recycle();//
        options.inSampleSize = scale;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap outputBitmap = BitmapFactory.decodeFile(path, options);
        return outputBitmap;
    }

    /**
     * get
     */
}
