package com.eea.pms;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView forgetPassword, registerButton;
    Button loginButton;
    Intent intent;
    TextInputLayout nameError, passError;
    boolean isNameValid, isPasswordValid;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.btnRegister);
        forgetPassword = findViewById(R.id.btnForgetPassword);

        nameError = findViewById(R.id.nameError);
        passError = findViewById(R.id.passError);


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
            SharedPreferenceManager.getSharedPreferenceInstance(this).clear();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void onBtnLogin() {
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
        } else {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }
        if (isNameValid && isPasswordValid) {
            LoginRequest loginRequest = new LoginRequest(username1, password2);
            //authenticationService.login(loginRequest, this);
            Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                    .loginUser(loginRequest);
            login.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse != null) {
                            SharedPreferenceManager.getSharedPreferenceInstance(LoginActivity.this).saveUserSharedPref(loginResponse);
                            if (loginResponse.getRoles().equals("ROLE_ADMIN")) {
                                System.out.println("It is admin here testing");
                                intent = new Intent(getApplicationContext(), AdminDash.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                FancyToast.makeText(getApplicationContext(), "Successfully Logged In", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            } else if (loginResponse.getRoles().equals("ROLE_USER")) {
                                if (loginResponse.getStatus().equals("pending")) {
                                    FancyToast.makeText(getApplicationContext(), "Account pending  ", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                                } else {
                                    System.out.println("It is Pharmacist here");
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    FancyToast.makeText(getApplicationContext(), "Successfully Logged In", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                }
                            }
                        } else {
                            intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    FancyToast.makeText(getApplicationContext(), "Invalid Credentials  ", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).clear();
                }
            });

        } else {
            FancyToast.makeText(getApplicationContext(), "Error", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }

    public void onBtnRegister() {
        intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void onBtnForgotPassword() {
        intent = new Intent(getApplicationContext(), ResetPassword.class);
        startActivity(intent);
    }

}