package com.kvin.toolkit.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kvin.toolkit.utils.PersistentUtils;
import com.saker.app.huhu.constants.Keys;


/**
 * Created by sunmo on 2016/12/1.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    private static DBHelper mDbHelper;
    private static PersistentUtils mPersistentUtils;
    private static final String STORY_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + Tables.Media.class.getSimpleName() + "(" +
            Tables.Media._ID + " TEXT PRIMARY KEY," +
            Tables.Media._CATE_ID + " TEXT," +
            Tables.Media._CATE_NAME + " TEXT," +
            Tables.Media._TITLE + " TEXT," +
            Tables.Media._IMAGE + " TEXT," +
            Tables.Media._FILE_NAME + " TEXT," +
            Tables.Media._FILE_TYPE + " INTEGER," +//0 audio 1 video
            Tables.Media._DURATION + " TEXT," +
            Tables.Media._IS_DOWNLOADED + " INTEGER," +//0 not downloaded 1 downloaded 2 downloading
            Tables.Media._LOCAL_PATH + " TEXT," +
            Tables.Media._LAST_PLAY + " TEXT," +//
            Tables.Media._LAST_PLAY_DURATION + " TEXT" +
            ")";


    //get db name
    private static String getDbName(Context context) {
        mPersistentUtils = PersistentUtils.getInstance(context, PersistentUtils.XmlFile.USER_INFO);
        String ssoId = mPersistentUtils.readString(Keys.USER_SSO_ID);
        return ssoId + ".db";
    }

    //get db helper instance
    public static DBHelper getInstance(Context context) {
        if (mDbHelper == null) {
            mDbHelper = new DBHelper(context, getDbName(context), null, DB_VERSION);
        }
        return mDbHelper;
    }

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table here
        db.execSQL(STORY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //close db
    public void closeDb() {
        if (mDbHelper != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.close();
        }
        mDbHelper = null;
    }
}