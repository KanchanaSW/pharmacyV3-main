package com.eea.pms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eea.pms.DTO.Requests.LoginRequest;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.User;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AuthenticationApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;
import androidx.appcompat.app.AppCompatActivity;

import com.eea.pms.CallBacks.ResponseCallback;
import com.eea.pms.RetrofitAPIService.AuthenticationService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ResponseCallback {

    EditText username;
    EditText password;
    TextView forgetPassword,registerButton;
    Button loginButton;
    Intent intent;
    TextInputLayout nameError,passError;
    boolean isNameValid,isPasswordValid;
    public AuthenticationService authenticationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authenticationService = new AuthenticationService();
        username=findViewById(R.id.etUsername);
        password=findViewById(R.id.etPassword);
        loginButton=findViewById(R.id.btnLogin);
        registerButton=findViewById(R.id.btnRegister);
        forgetPassword=findViewById(R.id.btnForgetPassword);

        nameError = findViewById(R.id.nameError);passError = findViewById(R.id.passError);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLogin();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnRegister();
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnForgotPassword();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPreferenceManager.getSharedPreferenceInstance(this).isLoggedIn()) {
            System.out.println(!SharedPreferenceManager.getSharedPreferenceInstance(this).isLoggedIn() + " +logged in ***********");
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    public void onBtnLogin(){
        String username1 = username.getText().toString();
        String password2 = password.getText().toString();
        if (username1.isEmpty()) {
            nameError.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }
        if (password2.isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        }else {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }
        if(isNameValid && isPasswordValid){
             LoginRequest loginRequest = new LoginRequest(username1, password2);
            authenticationService.login(loginRequest, this);
        }
    }
    public void onBtnRegister(){
        intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
    public void onBtnForgotPassword(){

    }

    @Override
    public void onSuccess(Response response) {
        LoginResponse loginResponse = (LoginResponse) response.body();
        if (loginResponse != null) {
            //save the user in sharedpred
            SharedPreferenceManager.getSharedPreferenceInstance(LoginActivity.this).saveUserSharedPref(loginResponse);

            if (loginResponse.getRoles().equals("ROLE_ADMIN")) {
                System.out.println("It is admin here");
                intent = new Intent(this, MainActivityAdmin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            } else if(loginResponse.getRoles().equals("ROLE_USER")) {
                System.out.println("It is Pharmacist here");
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
            FancyToast.makeText(getApplicationContext(), "Successfully Logged In", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        }else{
            System.out.println("//////onSuccess//// loginResponse null");
        }
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println("errorMessage: *****" + errorMessage);
        FancyToast.makeText(this, "Invalid Credentials  ", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }
}