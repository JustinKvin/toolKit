package com.kvin.toolkit.db;

/**
 * Created by Kvin on 2017/2/17.
 */
public class Tables {
    /**
     * story table
     * length=12
     */
    public static final class Media {
        public static final String _ID = "_id";
        public static final String _CATE_ID = "_cate_id";
        public static final String _CATE_NAME = "_cate_name";
        public static final String _IMAGE = "_image";
        public static final String _TITLE = "_title";
        public static final String _FILE_NAME = "_file_name";
        public static final String _FILE_TYPE = "_file_type";//0 audio 1 video
        public static final String _DURATION = "_duration";
        public static final String _IS_DOWNLOADED = "_is_downloaded";
        public static final String _LOCAL_PATH = "_local_path";//storing path if downloaded
        public static final String _LAST_PLAY = "_last_play";
        public static final String _LAST_PLAY_DURATION = "_last_play_duration";
    }
}
