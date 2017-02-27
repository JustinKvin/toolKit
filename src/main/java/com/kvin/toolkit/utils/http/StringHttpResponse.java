package com.kvin.toolkit.utils.http;

import java.io.IOException;

/**
 * Created by SC-002 on 2016/12/13.
 */
public interface StringHttpResponse {
    void onSuccess(String response);

    void onPreExecute();

    void onFailure(IOException e);
}
