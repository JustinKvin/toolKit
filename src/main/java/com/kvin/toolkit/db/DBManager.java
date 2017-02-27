package com.kvin.toolkit.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kvin.toolkit.utils.StringUtils;
import com.kvin.toolkit.utils.ToastUtils;
import com.saker.app.huhu.R;
import com.saker.app.huhu.bean.MediaBean;

import java.util.ArrayList;

/**
 * Created by sunmo on 2016/12/2.
 */
public class DBManager {
    private static DBManager mDBManager;
    private DBHelper mDBHelper;
    private Context mContext;
    private SQLiteDatabase db;
    private String sql;

    private DBManager(Context context) {
        this.mContext = context;
        mDBHelper = DBHelper.getInstance(context);
        db = mDBHelper.getWritableDatabase();
    }

    /**
     * @param context
     * @return a db manager
     */
    public static DBManager getInstance(Context context) {
        if (mDBManager == null) {
            mDBManager = new DBManager(context);
        }
        return mDBManager;
    }

    //insert multi contacts
    public void insertObjects(ArrayList<MediaBean> items) {
        if (items == null || items.size() == 0) {
            ToastUtils.simpleToast(mContext, "插入数据不能为空");
            return;
        }
        for (MediaBean b : items) {
            insertObject(b);
        }
    }

    //insert single obj
    public void insertObject(MediaBean b) {
        if (b == null) {
            ToastUtils.simpleToast(mContext, mContext.getResources().getString(R.string.db_insert_null));
            return;
        }
        String sql = "INSERT INTO " + Tables.Media.class.getSimpleName() + "("
                + Tables.Media._ID + ","
                + Tables.Media._CATE_ID + ","
                + Tables.Media._CATE_NAME + ","
                + Tables.Media._TITLE + ","
                + Tables.Media._IMAGE + ","
                + Tables.Media._FILE_NAME + ","
                + Tables.Media._FILE_TYPE + ","
                + Tables.Media._DURATION + ","
                + Tables.Media._IS_DOWNLOADED + ","
                + Tables.Media._LOCAL_PATH + ","
                + Tables.Media._LAST_PLAY + ","
                + Tables.Media._LAST_PLAY_DURATION + ")"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql, new Object[]{b.getMediaId(), b.getCateId(), b.getCateName(), b.getTitle(), b.getImage(),
                b.getFileName(), b.getFileType(), b.getDuration(), b.getIsDownloaded(), b.getLocalPath(), b.getLastPlay(), b.getLastPlayDuration()});
    }


    /**
     * delete
     */
    public void deleteObject(MediaBean b) {
        if (b == null) {
            ToastUtils.simpleToast(mContext, mContext.getString(R.string.db_delete_null));
            return;
        }
        sql = "DELETE FROM " + Tables.Media.class.getSimpleName()
                + " WHERE " + Tables.Media._ID + "=" + b.getMediaId();
        db.execSQL(sql);
    }

    /**
     * update
     */
    public void updateObject(MediaBean b) {
        if (b == null) {
            ToastUtils.simpleToast(mContext, mContext.getString(R.string.db_update_null));
            return;
        }
        sql = "UPDATE " + Tables.Media.class.getSimpleName() + " SET "
                + Tables.Media._CATE_ID + "=?,"
                + Tables.Media._CATE_NAME + "=?,"
                + Tables.Media._TITLE + "=?,"
                + Tables.Media._IMAGE + "=?,"
                + Tables.Media._FILE_NAME + "=?,"
                + Tables.Media._FILE_TYPE + "=?,"
                + Tables.Media._DURATION + "=?,"
                + Tables.Media._IS_DOWNLOADED + "=?,"
                + Tables.Media._LOCAL_PATH + "=?,"
                + Tables.Media._LAST_PLAY + "=?,"
                + Tables.Media._LAST_PLAY_DURATION + "=? "
                + "WHERE " + Tables.Media._ID + "=" + b.getMediaId();
        db.execSQL(sql, new Object[]{b.getCateId(), b.getCateName(), b.getTitle(), b.getImage(),
                b.getFileName(), b.getFileType(), b.getDuration(), b.getIsDownloaded(), b.getLocalPath(),
                b.getLastPlay(), b.getLastPlayDuration()});
    }


    /**
     * @param field
     * @param value1
     * @param value2
     * @param id
     */
    public void updateObject(String field, String value1, int value2, String id) {
        if (StringUtils.isEmpty(field) || StringUtils.isEmpty(id)) {
            ToastUtils.simpleToast(mContext, mContext.getString(R.string.db_update_null));
            return;
        }
        sql = "UPDATE " + Tables.Media.class.getSimpleName() + " SET "
                + field + "=? "
                + "WHERE " + Tables.Media._ID + "=" + id;
        if (StringUtils.isEmpty(value1)) {
            db.execSQL(sql, new Object[]{value2});
        } else {
            db.execSQL(sql, new Object[]{value1});
        }
    }

    /**
     * update download status
     */
    public void updateObject(String field, int value, String id) {
        this.updateObject(field, null, value, id);
    }

    /**
     * update  play duration
     */
    public void updateObject(String field, String value, String id) {
        this.updateObject(field, value, -1, id);
    }


    //query invite info
    public ArrayList<MediaBean> queryObjects() {
        ArrayList<MediaBean> results = new ArrayList<>();
        Cursor cursor = db.query(Tables.Media.class.getSimpleName(), null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            MediaBean bean = new MediaBean();
            bean.setMediaId(cursor.getString(cursor.getColumnIndex(Tables.Media._ID)));
            bean.setCateId(cursor.getString(cursor.getColumnIndex(Tables.Media._CATE_ID)));
            bean.setCateName(cursor.getString(cursor.getColumnIndex(Tables.Media._CATE_NAME)));
            bean.setTitle(cursor.getString(cursor.getColumnIndex(Tables.Media._TITLE)));
            bean.setImage(cursor.getString(cursor.getColumnIndex(Tables.Media._IMAGE)));
            bean.setFileName(cursor.getString(cursor.getColumnIndex(Tables.Media._FILE_NAME)));
            bean.setFileType(cursor.getInt(cursor.getColumnIndex(Tables.Media._FILE_TYPE)));
            bean.setDuration(cursor.getString(cursor.getColumnIndex(Tables.Media._DURATION)));
            bean.setIsDownloaded(cursor.getInt(cursor.getColumnIndex(Tables.Media._IS_DOWNLOADED)));
            bean.setLocalPath(cursor.getString(cursor.getColumnIndex(Tables.Media._LOCAL_PATH)));
            bean.setLastPlay(cursor.getString(cursor.getColumnIndex(Tables.Media._LAST_PLAY)));
            bean.setLastPlayDuration(cursor.getString(cursor.getColumnIndex(Tables.Media._LAST_PLAY_DURATION)));
            results.add(bean);
        }
        cursor.close();//don`t forget
        return results;
    }

    //query by id
    public MediaBean queryById(String id) {
        MediaBean bean = null;
        Cursor cursor = db.query(Tables.Media.class.getSimpleName(), null, Tables.Media._ID + "=?", new String[]{id}, null, null, null);
        while (cursor.moveToNext()) {
            bean = new MediaBean();
            bean.setMediaId(cursor.getString(cursor.getColumnIndex(Tables.Media._ID)));
            bean.setCateId(cursor.getString(cursor.getColumnIndex(Tables.Media._CATE_ID)));
            bean.setCateName(cursor.getString(cursor.getColumnIndex(Tables.Media._CATE_NAME)));
            bean.setTitle(cursor.getString(cursor.getColumnIndex(Tables.Media._TITLE)));
            bean.setImage(cursor.getString(cursor.getColumnIndex(Tables.Media._IMAGE)));
            bean.setFileName(cursor.getString(cursor.getColumnIndex(Tables.Media._FILE_NAME)));
            bean.setFileType(cursor.getInt(cursor.getColumnIndex(Tables.Media._FILE_TYPE)));
            bean.setDuration(cursor.getString(cursor.getColumnIndex(Tables.Media._DURATION)));
            bean.setIsDownloaded(cursor.getInt(cursor.getColumnIndex(Tables.Media._IS_DOWNLOADED)));
            bean.setLocalPath(cursor.getString(cursor.getColumnIndex(Tables.Media._LOCAL_PATH)));
            bean.setLastPlay(cursor.getString(cursor.getColumnIndex(Tables.Media._LAST_PLAY)));
            bean.setLastPlayDuration(cursor.getString(cursor.getColumnIndex(Tables.Media._LAST_PLAY_DURATION)));
        }
        cursor.close();//don`t forget
        return bean;
    }

    /**
     * lazy insert:judge whether it exists then schema how to update
     */
    public void lazyUpdateLoadStatus(MediaBean bean) {
        if (bean == null) {
            ToastUtils.simpleToast(mContext, "抱歉，不能插入空对象");
            return;
        }
        MediaBean b = queryById(bean.getMediaId());
        if (b == null) {
            insertObject(bean);
        } else {
            //load status
            updateObject(Tables.Media._IS_DOWNLOADED, bean.getIsDownloaded(), bean.getMediaId());
        }
    }

    public void lazyUpdateLastPlay(MediaBean bean) {
        if (bean == null) {
            ToastUtils.simpleToast(mContext, "抱歉，不能插入空对象");
            return;
        }
        MediaBean b = queryById(bean.getMediaId());
        if (b == null) {
            insertObject(bean);
        } else {
            //load status
            updateObject(Tables.Media._LAST_PLAY, bean.getLastPlay(), bean.getMediaId());
        }
    }

    public void lazyUpdateLastPlayDuration(MediaBean bean) {
        if (bean == null) {
            ToastUtils.simpleToast(mContext, "抱歉，不能插入空对象");
            return;
        }
        MediaBean b = queryById(bean.getMediaId());
        if (b == null) {
            insertObject(bean);
        } else {
            //load status
            updateObject(Tables.Media._LAST_PLAY_DURATION, bean.getLastPlayDuration(), bean.getMediaId());
        }
    }

    /**
     * close
     */
    public void close() {
        if (mDBHelper != null)
            mDBHelper.closeDb();
    }


}
