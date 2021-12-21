package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eea.pms.DTO.Responses.MessageResponse;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AuthenticationApi;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateOTP extends AppCompatActivity {

    EditText etOTPNumber;
    Button btnValidateOTP;
    private ProgressBar pgsBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_otp);
        etOTPNumber=findViewById(R.id.etOTPnumber);
        pgsBar = (ProgressBar) findViewById(R.id.pBarV);
        btnValidateOTP=findViewById(R.id.btnValidateOTP);
        
        btnValidateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgsBar.setVisibility(v.VISIBLE);
                validateOTP();
            }
        });
    }

    private void validateOTP() {
       Integer otpNumber= Integer.valueOf(etOTPNumber.getText().toString());
       if (otpNumber.toString().isEmpty()){
           FancyToast.makeText(getApplicationContext(), "OTP required", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
       }else{

           Call<MessageResponse> validateOtp= RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class).validCheck(otpNumber);
           validateOtp.enqueue(new Callback<MessageResponse>() {
               @Override
               public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                   pgsBar.setVisibility(View.GONE);
                   startActivity(new Intent(getApplicationContext(), NewPassword.class).putExtra("otpNumber",otpNumber));
               }

               @Override
               public void onFailure(Call<MessageResponse> call, Throwable t) {
                   pgsBar.setVisibility(View.GONE);
                   FancyToast.makeText(getApplicationContext(), "Invalid", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
               }
           });
      /*     validateOtp.enqueue(new Callback<Integer>() {
               @Override
               public void onResponse(Call<Integer> call, Response<Integer> response) {
                   startActivity(new Intent(getApplicationContext(), NewPassword.class).putExtra("otpNumber",otpNumber));

               }

               @Override
               public void onFailure(Call<Integer> call, Throwable t) {
                   FancyToast.makeText(getApplicationContext(), "Invalid", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

               }
           });*/
       }
    }
}














