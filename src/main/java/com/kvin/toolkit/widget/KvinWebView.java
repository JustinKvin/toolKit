package com.kvin.toolkit.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Kvin on 2017/1/19.
 */
public class KvinWebView extends WebView {

    public KvinWebView(Context context) {
        super(context);
        init(context);
    }


    public KvinWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KvinWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KvinWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    /**
     * confi setting info
     *
     * @param context
     */
    private void init(Context context) {
        getSettings().setSupportZoom(false);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
    }

    /**
     * set cathe directory
     */
    public void setCatheDirctory(String path) {
        getSettings().setAppCacheEnabled(true);
        getSettings().setAppCachePath(path);
    }

}
