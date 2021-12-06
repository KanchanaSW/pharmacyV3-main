package com.eea.pms.CallBacks;

import retrofit2.Response;

public interface ResponseCallback {
    void onSuccess(Response response);
    void onError(String errorMessage);
}
