package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.MessageResponse;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AuthenticationApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    EditText etEmailResetPassword;
    Button btnSendEmail;
    private ProgressBar pgsBar;
    private LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        pgsBar = (ProgressBar) findViewById(R.id.pBarRP);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();

        etEmailResetPassword=findViewById(R.id.etEmailResetP);
        btnSendEmail=findViewById(R.id.btnSendEmail);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgsBar.setVisibility(v.VISIBLE);
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        String email=etEmailResetPassword.getText().toString();
        if (email.isEmpty()){
            FancyToast.makeText(getApplicationContext(), "Email required", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }else{
            //String jwtToken="Bearer "+loginResponse.getToken();
            Call<MessageResponse> sendEmail= RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class).sendOTPEmail(email);
            sendEmail.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful()){
                        //start the fragment
                        pgsBar.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(), ValidateOTP.class));
                        FancyToast.makeText(getApplicationContext(), " Success", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                    }else{
                        //start the error fragment
                        pgsBar.setVisibility(View.GONE);
                        FancyToast.makeText(getApplicationContext(), " error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    pgsBar.setVisibility(View.GONE);
                    FancyToast.makeText(getApplicationContext(), "Invalid", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            });
        }
    }
}










