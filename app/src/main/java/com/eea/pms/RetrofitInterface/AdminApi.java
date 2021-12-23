package com.eea.pms.RetrofitInterface;

import com.eea.pms.Model.Item;
import com.eea.pms.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AdminApi {
    //get all users
    @GET("api/users/all")
    Call<List<User>> getAllUsers(@Header("Authorization") String token);
}
