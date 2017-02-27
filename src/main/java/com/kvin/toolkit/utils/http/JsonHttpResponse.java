package com.kvin.toolkit.utils.http;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by SC-002 on 2016/12/13.
 */
public interface JsonHttpResponse {
    void onSuccess(JSONObject response);

    void onPreExecute();

    void onFailure(IOException e);
}
