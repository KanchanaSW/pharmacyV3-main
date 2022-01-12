package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.User;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdateAcc extends AppCompatActivity {
    DrawerLayout drawerLayoutUser;
    EditText etEmailUpdateUser,etUsernameUpdateUser,etNoUpdateUser,etPasswordUpdateUser,etPassword2UpdateUser,etUserIdUser;
    TextInputLayout emailError,nameError,phoneError,passError,passError2;
    boolean isPass1Valid,isPass2Valid,isPhoneValid;
    Button btnUpdateAccountUser;
    LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_acc);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        
        drawerLayoutUser = findViewById(R.id.drawer_layout_user);
        etUserIdUser=findViewById(R.id.etUserIdUser);
        //set the user id visibiliy gone
        etUserIdUser.setVisibility(View.GONE);
        etEmailUpdateUser=findViewById(R.id.etEmailUpdateUser);etEmailUpdateUser.setEnabled(false);
        etUsernameUpdateUser=findViewById(R.id.etUsernameUpdateUser);etUsernameUpdateUser.setEnabled(false);
        etNoUpdateUser=findViewById(R.id.etNoUpdateUser);
        etPasswordUpdateUser=findViewById(R.id.etPasswordUpdateUser);
        etPassword2UpdateUser=findViewById(R.id.etPassword2UpdateUser);
        emailError=findViewById(R.id.emailError);
        nameError=findViewById(R.id.nameError);
        phoneError=findViewById(R.id.phoneError);
        passError=findViewById(R.id.passError);
        passError2=findViewById(R.id.passError2);

        TextView mtaHeading=findViewById(R.id.mtuHeading);
        mtaHeading.setText("Update My Account");
        //get user details function initiate
        getUserDetails();

        btnUpdateAccountUser=findViewById(R.id.btnUpdateAccountUser);
        btnUpdateAccountUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });
    }

    private void updateAccount() {
        try{
            String uId=etUserIdUser.getText().toString();
            String email=etEmailUpdateUser.getText().toString();
            String uName=etUsernameUpdateUser.getText().toString();
            String phone=etNoUpdateUser.getText().toString();
            String p1=etPasswordUpdateUser.getText().toString();
            String p2=etPassword2UpdateUser.getText().toString();

            if (phone.isEmpty()){
                phoneError.setError(getResources().getString(R.string.phone_error));
                isPhoneValid=false;
            }else if (phone.length() != 10){
                phoneError.setError("Phone number invalid");
                isPhoneValid=false;
            }else {
                isPhoneValid=true;
                phoneError.setErrorEnabled(false);
            }
            //pass validations
            if (p1.isEmpty()) {
                passError.setError(getResources().getString(R.string.password_error));
                isPass1Valid = false;
            } else if (p2.isEmpty()) {
                passError2.setError(getResources().getString(R.string.password_error));
                isPass1Valid = false;
            } else if (!p1.equals(p2)) {
                passError.setError(getResources().getString(R.string.password_dont_match_error));
                passError2.setError(getResources().getString(R.string.password_dont_match_error));
                isPass1Valid = false;
            } else if (p1.length() < 6) {
                passError.setError(getResources().getString(R.string.error_invalid_password));
                isPass1Valid = false;
            } else {
                isPass1Valid = true;
                passError.setErrorEnabled(false);
                passError2.setErrorEnabled(false);
            }

            if (isPhoneValid && isPass1Valid){
                User updateUser=new User(Integer.parseInt(uId),phone,p1);
                System.out.println("updated user =>>"+updateUser);
                String jwtToken="Bearer "+loginResponse.getToken();
                Call<String> uUser= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).updateMyAcc(updateUser,jwtToken);
                uUser.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        FancyToast.makeText(getApplicationContext(), "Account updated SUCCESS!", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        FancyToast.makeText(getApplicationContext(), "update failed", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                });

            }
        }catch (Exception e){
            System.out.println("Update exception => "+e);
            FancyToast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }

    private void getUserDetails() {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<User> getUser= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).get(jwtToken);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u=response.body();
                if (u != null){
                    etUserIdUser.setText(String.valueOf(u.getUserId()));
                    etEmailUpdateUser.setText(String.valueOf(u.getEmail()));
                    etUsernameUpdateUser.setText(String.valueOf(u.getUsername()));
                    etNoUpdateUser.setText(String.valueOf(u.getPhone()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }


    ///////////    User nav functions           /////////////////////////////////////////////////////////
    public void ClickLogoUser(View view) {
        MainActivity.closeDrawerUser(drawerLayoutUser);
    }

    public void ClickMenuUser(View view) {
        MainActivity.openDrawerUser(drawerLayoutUser);
    }

    public void ClickHomeUser(View view) {
        MainActivity.redirectActivityU(this, MainActivity.class);
    }

    public void ClickMyAccountUser(View view) {
        recreate();
    }

    public void ClickProducts(View view) {
        MainActivity.redirectActivityU(this, UserItemList.class);
    }

    public void ClickOrders(View view) {
        MainActivity.redirectActivityU(this, UserOrderList.class);
    }

    public void ClickRequests(View view) {
        MainActivity.redirectActivityU(this, UserRequestList.class);
    }

    public void ClickInquires(View view) {
        MainActivity.redirectActivityU(this, UserInquiryList.class);
    }

    public void ClickLogOutUser(View view) {
        logOutUser(this);
    }

    public void logOutUser(Activity mainActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).clear();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawerUser(drawerLayoutUser);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}