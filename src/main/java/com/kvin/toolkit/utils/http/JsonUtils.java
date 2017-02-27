//package com.hezi.toolkit.utils.http;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo.State;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//
//import com.squareup.okhttp.Call;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.MultipartBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by Kvin on 2015/12/26.
// */
//public class JsonUtils {
//    //judge whether an url is available
//    private boolean isAvailable;
//    //    private final MediaType MEDIA_TYPE = MediaType.parse("image/png;charset=utf-8");
//    private final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream;charset=utf-8");
//    private final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain;charset=utf-8");
//    private final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
//    private final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg;charset=utf-8");
//    private final int SUCCESS = 1;
//    private final int FAILURE = 2;
//    private final int PRE_EXECUTE = 3;
//    private JsonHttpResponse mJsonHttpResponse;
//    private StringHttpResponse mStringHttpResponse;
//    private OnImageAccessListener imageAccessListener;
//    private Handler handler;
//    private OkHttpClient client;
//
//    private static JsonUtils utils;
//
//    //request complete then call cancel
//    private Call call;
//
//    private State wifiState = State.CONNECTED;
//    private State mobileState = State.CONNECTED;
//
//    //use constructor to create new Object when should handling many requests
//    public JsonUtils() {
//        initClient();
//
//    }
//
//    private void initClient() {
//        //set UI here
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what) {
//                    case SUCCESS://request success
//                        if (mJsonHttpResponse == null) break;
//                        mJsonHttpResponse.onSuccess((JSONObject) msg.obj);
//                        break;
//                    case FAILURE://request failure
//                        if (mJsonHttpResponse == null) break;
//                        mJsonHttpResponse.onFailure((IOException) msg.obj);
//                        break;
//                    case PRE_EXECUTE:
//                        if (mJsonHttpResponse == null) break;
//                        mJsonHttpResponse.onPreExecute();
//                        break;
//                }
//            }
//        };
//        client = new OkHttpClient();
//
//        client.setConnectTimeout(10, TimeUnit.SECONDS);//set timeout
//    }
//
//    //single Instance
//    public static JsonUtils newInstance() {
//        if (utils == null) {
//            utils = new JsonUtils();
//        }
//        return utils;
//    }
//
//    //get request without tag
//    public synchronized void get(String path, JsonHttpResponse ck) {
//        this.get(path, null, ck);
//    }
//
//    //get request with tag
//    public synchronized void get(String path, String tag, JsonHttpResponse ck) {
//        mJsonHttpResponse = ck;
//        handler.sendEmptyMessage(PRE_EXECUTE);
//        final Request request = new Request.Builder()
//                .url(path)
//                .tag(tag)
//                .build();
//        call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                final Message msg = Message.obtain();
//                msg.what = FAILURE;
//                msg.obj = e;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {//execute in childThread
//                Message msg = Message.obtain();
//                msg.what = SUCCESS;
//                String str = response.body().string();
//                if (StringUtils.isJSONObject(str)) {
//                    msg.obj = StringUtils.getJSONObject();
//                }
//                handler.sendMessage(msg);
//            }
//
//        });
////        if (getNetState() == State.DISCONNECTED) {
////            handler.postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    handler.sendEmptyMessage(FAILURE);
////                }
////            }, Sizes.ANIM_DELAY);
////            handler.sendEmptyMessage(FAILURE);
////        }
//    }
//
//    //get string
//    public synchronized void getString(String path, String tag, StringHttpResponse ck) {
//        mStringHttpResponse = ck;
//        handler.sendEmptyMessage(PRE_EXECUTE);
//        final Request request = new Request.Builder()
//                .url(path)
//                .tag(tag)
//                .build();
//        call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                final Message msg = Message.obtain();
//                msg.what = FAILURE;
//                msg.obj = e;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {//execute in childThread
//                Message msg = Message.obtain();
//                msg.what = SUCCESS;
//                String str = response.body().string();
//                handler.sendMessage(msg);
//            }
//
//        });
//    }
//
//    //get current net state
//    private State getNetState() {
//        ConnectivityManager manager = (ConnectivityManager) BaseApp.newInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
//        State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//        State mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//        if ((wifiState == State.DISCONNECTED || wifiState == State.DISCONNECTING) && (mobileState == State.DISCONNECTED) || mobileState == State.DISCONNECTING) {
//            return State.DISCONNECTED;
//        }
//        return null;
//    }
//
//    //post request with params and page
//    public synchronized void post(String path, Map<String, String> params, JsonHttpResponse ck) {
//        mJsonHttpResponse = ck;
//        handler.sendEmptyMessage(PRE_EXECUTE);
//        OkHttpClient client = new OkHttpClient();
//        FormEncodingBuilder builder = new FormEncodingBuilder();
//        if (params != null) {
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                builder.add(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
//            }
//        }
//        final Request request = new Request.Builder()
//                .url(path)
//                .post(builder.build())
//                .build();
//        call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Message msg = Message.obtain();
//                msg.what = FAILURE;
//                msg.obj = e;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Message msg = Message.obtain();
//                msg.what = SUCCESS;
//                String str = response.body().string();
//                if (StringUtils.isJSONObject(str)) {
//                    msg.obj = StringUtils.getJSONObject();
//                }
//                handler.sendMessage(msg);
//            }
//        });
//        if (getNetState() == State.DISCONNECTED) {
//            handler.sendEmptyMessage(FAILURE);
//        }
//    }
//
//
////    //post request without page
////    public synchronized void post(String path, Map<String, String> params, JsonCallBack ck) {
////        this.post(path, params, ConsBean.NO_PAGE, ck);
////    }
//
//
//    //upload image
//    public synchronized void uploadFile(String path, HashMap<String, String> params, String key, File[] files, Bitmap bitmap, JsonHttpResponse ck) {
//        mJsonHttpResponse = ck;
//        handler.sendEmptyMessage(PRE_EXECUTE);
//        OkHttpClient client = new OkHttpClient();
//        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
//        for (Map.Entry<String, String> p : params.entrySet()) {
//            multipartBuilder.addFormDataPart(p.getKey(), p.getValue());
//        }
//        if (bitmap != null) {
//            try {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                baos.flush();
//                multipartBuilder.addFormDataPart(key, "default", RequestBody.create(MEDIA_TYPE, baos.toByteArray()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (files != null && files.length > 0) {
//            for (File file : files) {
//                multipartBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MEDIA_TYPE, file));
//            }
//        }
//        final Request request = new Request.Builder()
//                .url(path)
//                .post(multipartBuilder.build())
//                .build();
//        call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Message msg = Message.obtain();
//                msg.what = FAILURE;
//                msg.obj = e;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Message msg = Message.obtain();
//                msg.what = SUCCESS;
//                String str = response.body().string();
//                if (StringUtils.isJSONObject(str)) {
//                    msg.obj = StringUtils.getJSONObject();
//                }
//                handler.sendMessage(msg);
//            }
//        });
//        if (getNetState() == State.DISCONNECTED) {
//            handler.sendEmptyMessage(FAILURE);
//        }
//    }
//
//    //upload a set of files
//    public synchronized void uploadFile(String path, HashMap<String, String> params, String key, File[] files, JsonHttpResponse ck) {
//        this.uploadFile(path, params, key, files, null, ck);
//    }
//
//    //upload a single picture
//    public synchronized void uploadFile(String path, HashMap<String, String> params, String key, File file, JsonHttpResponse ck) {
//        mJsonHttpResponse = ck;
//        handler.sendEmptyMessage(PRE_EXECUTE);
//        OkHttpClient client = new OkHttpClient();
//        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
//        if (params == null) return;
////        for (Map.Entry<String, String> param : params.entrySet()) {
////            multipartBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.getKey() + "\""),
////                    RequestBody.create(null, param.getValue()));
////        }
//        for (Map.Entry<String, String> p : params.entrySet()) {
//            multipartBuilder.addFormDataPart(p.getKey(), p.getValue());
//        }
//        if (file == null) return;
////        multipartBuilder.addPart(Headers.of("Content-Disposition",
////                "form-data; name=\"" + key + "\"; filename=\"" + file.getName() + "\""),
////                RequestBody.create(MEDIA_TYPE, file));
//        multipartBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MEDIA_TYPE_JPG, file));
//        final Request request = new Request.Builder()
//                .url(path)
//                .post(multipartBuilder.build())
//                .build();
//        call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Message msg = Message.obtain();
//                msg.what = FAILURE;
//                msg.obj = e;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Message msg = Message.obtain();
//                msg.what = SUCCESS;
//                String str = response.body().string();
//                if (StringUtils.isJSONObject(str)) {
//                    msg.obj = StringUtils.getJSONObject();
//                }
//                handler.sendMessage(msg);
//            }
//        });
//        if (getNetState() == State.DISCONNECTED) {
//            handler.sendEmptyMessage(FAILURE);
//        }
//    }
//
//    public synchronized void uploadFile(String path, HashMap<String, String> params, String key, Bitmap bitmap, JsonHttpResponse ck) {
//        this.uploadFile(path, params, key, null, bitmap, ck);
//    }
//
//    //judge whether an url is available
//    public synchronized void setOnImageAccessListener(final String path, OnImageAccessListener imageAccessListener) {
//        this.imageAccessListener = imageAccessListener;
//        if (TextUtils.isEmpty(path) || !WebUtils.invalid(path.trim())) {
//            handler.sendEmptyMessage(FAILURE);
//            return;
//        }
//
//        final Request request = new Request.Builder()
//                .url(path)
//                .build();
//        call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                handler.sendEmptyMessage(FAILURE);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {//execute in childThread
//                if (response.code() == 200) {
//                    handler.sendEmptyMessage(SUCCESS);
//                } else {
//                    handler.sendEmptyMessage(FAILURE);
//                }
//            }
//
//        });
//
//    }
//
//
//    //set request cancel method
//    public void requestCancel(String tag) {
//        if (client != null)
//            client.cancel(tag);
////        if (call != null && !call.isCanceled()) {
////            call.cancel();
////        }
//    }
//
//
//    public interface OnImageAccessListener {
//        void onImageAccess(boolean access);
//    }
//}
