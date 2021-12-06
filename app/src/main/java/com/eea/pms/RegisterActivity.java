package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eea.pms.CallBacks.ResponseCallback;
import com.eea.pms.Model.User;
import com.eea.pms.RetrofitAPIService.AuthenticationService;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Pattern;

import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements ResponseCallback {
    public AuthenticationService authenticationService;
    Intent intent;
    Button btnRegister;
    EditText etUsername, etNo, etEmail, etPass1, etPass2;
    TextInputLayout nameError, emailError, phoneError, passError, passError2;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        authenticationService = new AuthenticationService();

        nameError = findViewById(R.id.nameError);
        emailError = findViewById(R.id.emailError);
        phoneError = findViewById(R.id.phoneError);
        passError = findViewById(R.id.passError);
        passError2 = findViewById(R.id.passError2);

        etUsername = findViewById(R.id.etUsernameR);
        etNo = findViewById(R.id.etNoR);
        etEmail = findViewById(R.id.etEmailR);
        etPass1 = findViewById(R.id.etPasswordR1);
        etPass2 = findViewById(R.id.etPasswordR2);
        btnRegister = findViewById(R.id.btnRegisterR);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnRegister();
            }
        });

    }

    private void onRedirectLogin() {
        intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void onBtnRegister() {
        try {
            String u1 = etUsername.getText().toString();
            String no = etNo.getText().toString();
            String email = etEmail.getText().toString();
            String p1 = etPass1.getText().toString();
            String p2 = etPass2.getText().toString();

            if (u1.isEmpty()) {
                nameError.setError(getResources().getString(R.string.name_error));
                isNameValid = false;
            } else {
                isNameValid = true;
                nameError.setErrorEnabled(false);
            }

            if (no.isEmpty()) {
                phoneError.setError(getResources().getString(R.string.phone_error));
                isPhoneValid = false;
            } else {
                isPhoneValid = true;
                phoneError.setErrorEnabled(false);
            }

            if (email.isEmpty()) {
                emailError.setError(getResources().getString(R.string.error_invalid_email));
                isEmailValid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailError.setError(getResources().getString(R.string.error_invalid_email));
                isEmailValid = false;
            } else {
                isEmailValid = true;
                emailError.setErrorEnabled(false);
            }

            if (p1.isEmpty()) {
                passError.setError(getResources().getString(R.string.password_error));
                isPasswordValid = false;
            } else if (p2.isEmpty()) {
                passError2.setError(getResources().getString(R.string.password_error));
                isPasswordValid = false;
            } else if (!p1.equals(p2)) {
                passError.setError(getResources().getString(R.string.password_dont_match_error));
                passError2.setError(getResources().getString(R.string.password_dont_match_error));
                isPasswordValid = false;
            } else if (p1.length() < 6) {
                passError.setError(getResources().getString(R.string.error_invalid_password));
                isPasswordValid = false;
            } else {
                isPasswordValid = true;
                passError.setErrorEnabled(false);
                passError2.setErrorEnabled(false);
            }

            if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
                User newUser = new User(email, u1, p1, no);
                System.out.println("//// new user " + newUser + newUser.getEmail());
                authenticationService.register(newUser, this);
            }

        } catch (Exception exception) {
            System.out.println("***** Register page register funct=> " + exception);
            FancyToast.makeText(getApplicationContext(), "Exception error...", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
        }
    }

    @Override
    public void onSuccess(Response response) {
        FancyToast.makeText(this, "Successfully Registered", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        onRedirectLogin();
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println("*****Register page => " + errorMessage);
        FancyToast.makeText(this, "Sorry something went wrong", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }
}