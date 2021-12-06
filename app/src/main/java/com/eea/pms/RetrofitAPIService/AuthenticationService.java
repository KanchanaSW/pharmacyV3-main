package com.eea.pms.RetrofitAPIService;

import android.telephony.TelephonyManager;

import com.eea.pms.CallBacks.CustomizeCallback;
import com.eea.pms.CallBacks.ResponseCallback;
import com.eea.pms.DTO.Requests.LoginRequest;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.User;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AuthenticationApi;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthenticationService {
    AuthenticationApi authenticationApi;
    RetrofitClient retrofitClient;
    public AuthenticationService() {
        this.authenticationApi = retrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class);
    }

    public void login(LoginRequest request, ResponseCallback callback) {
        Call<LoginResponse> loginResponseCall = authenticationApi.loginUser(request);
        //System.out.println("////////"+loginResponseCall.request().toString());
        loginResponseCall.enqueue(new CustomizeCallback<>(callback));
    }

    public void register(User user, ResponseCallback callback) {
        Call<User> userCall = authenticationApi.registerUser(user);
        userCall.enqueue(new CustomizeCallback<User>(callback));
    }
}
