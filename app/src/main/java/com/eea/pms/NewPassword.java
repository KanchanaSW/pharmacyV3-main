package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eea.pms.DTO.Responses.MessageResponse;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AuthenticationApi;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPassword extends AppCompatActivity {
    Button btnResetPassword;
    EditText etPassN1,etPassN2;
    TextInputLayout passError, passError2;
    boolean isPasswordValid;
    Integer otpNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        btnResetPassword=findViewById(R.id.btnResetPassword);
        etPassN1=findViewById(R.id.etPassN1);
        etPassN2=findViewById(R.id.etPassN2);
        passError = findViewById(R.id.passError);
        passError2 = findViewById(R.id.passError2);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            otpNumber=bundle.getInt("otpNumber");
        }else{
            otpNumber=0;
        }

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetNewPassword(otpNumber);
            }
        });
    }

    private void resetNewPassword(Integer otpNumber) {
        System.out.println(otpNumber);
        String p1 = etPassN1.getText().toString();
        String p2 = etPassN2.getText().toString();
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
        if (isPasswordValid){
            Call<MessageResponse> newPassword= RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class).resetPassword(otpNumber,p1);
            newPassword.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    FancyToast.makeText(getApplicationContext(), "Success", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    FancyToast.makeText(getApplicationContext(), "Error Happened.", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                }
            });
        }
    }
}