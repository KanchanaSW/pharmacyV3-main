package com.eea.pms.RetrofitInterface;

import com.eea.pms.DTO.Responses.MessageResponse;
import com.eea.pms.Model.Inquiry;
import com.eea.pms.Model.Item;
import com.eea.pms.Model.ItemRequests;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    //view my inquires
    @GET("api/inquiry/ViewMyInquires")
    Call<List<Inquiry>> getMyInquires(@Header("Authorization") String token);
    //add new Inquiry
    @POST("api/inquiry/item/{itemId}")
    Call<MessageResponse> addInquiry(@Path("itemId") Integer itemId, @Body Inquiry inquiry, @Header("Authorization") String token);

    //get user's requests
    @GET("api/requests/my-requests")
    Call<List<ItemRequests>> getMyRequests(@Header("Authorization") String token);
}
