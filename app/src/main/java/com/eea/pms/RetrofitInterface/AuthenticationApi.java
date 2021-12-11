package com.eea.pms.RetrofitInterface;

import com.eea.pms.DTO.Requests.LoginRequest;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.DTO.Responses.MessageResponse;
import com.eea.pms.Model.OTP;
import com.eea.pms.Model.User;

import retrofit.http.Path;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthenticationApi {
    @POST("api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<User> registerUser(@Body User user);

    @POST("api/otp/send-otp-change")
    Call<MessageResponse> sendOTPEmail(@Query("email") String email);

    @GET("api/otp/valid-check")
    Call<MessageResponse> validCheck(@Query("otpNumber") Integer otpNumber);

    @POST("api/otp/reset")
    Call<MessageResponse> resetPassword(@Query("otpNumber")Integer otpNumber, @Query("password") String password);
}
