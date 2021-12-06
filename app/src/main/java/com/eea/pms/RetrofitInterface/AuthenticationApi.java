package com.eea.pms.RetrofitInterface;

import com.eea.pms.DTO.Requests.LoginRequest;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {
    @POST("api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<User> registerUser(@Body User user);
}
