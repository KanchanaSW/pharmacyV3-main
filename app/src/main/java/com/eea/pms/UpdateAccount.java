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

import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.User;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAccount extends AppCompatActivity {

    DrawerLayout drawerLayoutAdmin;
    EditText etEmailUpdate,etUsernameUpdate,etNoUpdate,etPasswordUpdate,etPassword2Update,etUserId;
    TextInputLayout emailError,nameError,phoneError,passError,passError2;

    boolean isPass1Valid,isPass2Valid,isPhoneValid;
    Button btnUpdateAccount;
    LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        etUserId=findViewById(R.id.etUserId);
        //set the user id visibiliy gone
        etUserId.setVisibility(View.GONE);
        etEmailUpdate=findViewById(R.id.etEmailUpdate);etEmailUpdate.setEnabled(false);
        etUsernameUpdate=findViewById(R.id.etUsernameUpdate);etUsernameUpdate.setEnabled(false);
        etNoUpdate=findViewById(R.id.etNoUpdate);
        etPasswordUpdate=findViewById(R.id.etPasswordUpdate);
        etPassword2Update=findViewById(R.id.etPassword2Update);
        emailError=findViewById(R.id.emailError);
        nameError=findViewById(R.id.nameError);
        phoneError=findViewById(R.id.phoneError);
        passError=findViewById(R.id.passError);
        passError2=findViewById(R.id.passError2);

        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Update Account");
        //get user details function initiate
        getUserDetails();

        btnUpdateAccount=findViewById(R.id.btnUpdateAccount);
        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });
    }

    private void getUserDetails() {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<User> getUser= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).get(jwtToken);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u=response.body();
               if (u != null){
                   etUserId.setText(String.valueOf(u.getUserId()));
                   etEmailUpdate.setText(String.valueOf(u.getEmail()));
                   etUsernameUpdate.setText(String.valueOf(u.getUsername()));
                   etNoUpdate.setText(String.valueOf(u.getPhone()));
               }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void updateAccount() {
      try{
          String uId=etUserId.getText().toString();
          String email=etEmailUpdate.getText().toString();
          String uName=etUsernameUpdate.getText().toString();
          String phone=etNoUpdate.getText().toString();
          String p1=etPasswordUpdate.getText().toString();
          String p2=etPassword2Update.getText().toString();

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
                      startActivity(new Intent(getApplicationContext(), AdminDash.class));
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

    //navigation view ////////////////////////////////////////////////////////////////////////////////////
    public void ClickMenu(View view){
        AdminDash.openDrawer(drawerLayoutAdmin);
    }
    public void ClickLogo(View view){
        AdminDash.closeDrawer(drawerLayoutAdmin);
    }
    public void ClickHome(View view){
        AdminDash.redirectActivity(this,AdminDash.class);
    }
    public void ClickUsers(View view){
        AdminDash.redirectActivity(this,AdminUsersList.class);
    }
    public void ClickAccount(View view){
       recreate();
    }
    public void ClickProductsAdmin(View view){
        AdminDash.redirectActivity(this,ItemList.class);
    }
    public void ClickRequestsAdmin(View view){
        AdminDash.redirectActivity(this,AdminRequestsList.class);
    }
    public void ClickInquiresAdmin(View view){
        AdminDash.redirectActivity(this, AdminInquiresList.class);
    }
    public void ClickLogOut(View view){
        logOut(this);
    }
    public void logOut(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).clear();
                activity.finishAffinity();
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
        AdminDash.closeDrawer(drawerLayoutAdmin);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
}