package com.eea.pms.Model;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("message")
    private String messageResponse;

    public String getMessageResponse() {
        return messageResponse;
    }
}
