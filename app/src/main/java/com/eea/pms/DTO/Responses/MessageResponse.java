package com.eea.pms.DTO.Responses;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("message")
    private String messageResponse;

    public String getMessageResponse() {
        return messageResponse;
    }
}
