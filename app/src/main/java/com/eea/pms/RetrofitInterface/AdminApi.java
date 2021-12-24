package com.eea.pms.RetrofitInterface;

import com.eea.pms.Model.Item;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdminApi {
    //get all users
    @GET("api/users/all")
    Call<List<User>> getAllUsers(@Header("Authorization") String token);
    //get single user details
    @GET("api/users/user")
    Call<User> get( @Header("Authorization") String token);
    //update status of the user pending --> verified
    @GET("api/users/update-status/{userId}")
    Call<Void> updateUserStatus(@Path("userId") Integer userId, @Header("Authorization") String token);
    //update user account details
    @POST("api/users/update-user")
    Call<String> updateMyAcc(@Body User user, @Header("Authorization") String token);

    //get all item requests
    @GET("api/requests/all")
    Call<List<ItemRequests>> requestsList(@Header("Authorization") String token);
}
