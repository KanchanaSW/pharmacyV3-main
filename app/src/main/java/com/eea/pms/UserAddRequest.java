package com.eea.pms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.UserApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAddRequest extends AppCompatActivity {
    DrawerLayout drawerLayoutUser;
    private LoginResponse loginResponse;
    EditText etRequest, etItemNameRequest;
    Button btnAddRequest;
    TextInputLayout request_error, itemName_error;
    Boolean isValidItemName, isValidRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_request);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        drawerLayoutUser = findViewById(R.id.drawer_layout_user);
        TextView mtaHeading = findViewById(R.id.mtuHeading);
        mtaHeading.setText("Add new Request");

        etRequest = findViewById(R.id.etRequest);
        etItemNameRequest = findViewById(R.id.etItemNameRequest);
        btnAddRequest = findViewById(R.id.btnAddRequest);
        request_error = findViewById(R.id.request_error);
        itemName_error = findViewById(R.id.itemName_error);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_request);
        bottomNavigationView.setSelectedItemId(R.id.add_request);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.view_request_list:
                        startActivity(new Intent(getApplicationContext(), UserRequestList.class));
                        overridePendingTransition(0, 0);
                    case R.id.add_request:
                        return true;
                }
                return false;
            }
        });

        btnAddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequest();
            }
        });
    }

    private void addRequest() {
        String jwtToken = "Bearer " + loginResponse.getToken();
        String itemName = etItemNameRequest.getText().toString();
        String request = etRequest.getText().toString();

        if (itemName.isEmpty()) {
            itemName_error.setError("Requested Item name cannot be empty.");
            isValidItemName = false;
        } else if (itemName.length() < 6) {
            itemName_error.setError("Provide a name with at least 6 chars");
            isValidItemName = false;
        } else {
            isValidItemName = true;
            itemName_error.setErrorEnabled(false);
        }

        if (request.isEmpty()) {
            request_error.setError("Request note cannot be empty");
            isValidRequest = false;
        } else if (request.length() > 200) {
            request_error.setError("Maximum characters for note exceeded.");
            isValidRequest = false;
        } else if (request.length() < 10) {
            request_error.setError("Please provide at least minimum 10 chars");
            isValidRequest = false;
        } else {
            isValidRequest = true;
            request_error.setErrorEnabled(false);
        }

        if (isValidRequest && isValidItemName) {
            ItemRequests newRequest = new ItemRequests(itemName, request);
            Call<ItemRequests> addNew = RetrofitClient.getRetrofitClientInstance().create(UserApi.class).addNewRequest(newRequest, jwtToken);
            addNew.enqueue(new Callback<ItemRequests>() {
                @Override
                public void onResponse(Call<ItemRequests> call, Response<ItemRequests> response) {
                    FancyToast.makeText(getApplicationContext(), " Success", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    startActivity(new Intent(getApplicationContext(), UserRequestList.class));
                }

                @Override
                public void onFailure(Call<ItemRequests> call, Throwable t) {
                    FancyToast.makeText(getApplicationContext(), " Failed", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            });
        }
    }

    ///////////    User nav functions           /////////////////////////////////////////////////////////
    public void ClickLogoUser(View view){
        MainActivity.closeDrawerUser(drawerLayoutUser);
    }
    public void ClickMenuUser(View view){
        MainActivity.openDrawerUser(drawerLayoutUser);
    }
    public void ClickHomeUser(View view){
        MainActivity.redirectActivityU(this,MainActivity.class);
    }
    public void ClickMyAccountUser(View view){
        MainActivity.redirectActivityU(this,UserUpdateAcc.class);
    }
    public void ClickProducts(View view){
        MainActivity.redirectActivityU(this,UserItemList.class);
    }
    public void ClickOrders(View view){
        MainActivity.redirectActivityU(this,UserOrderList.class);
    }
    public void ClickRequests(View view){
        MainActivity.redirectActivityU(this,UserRequestList.class);
    }
    public void ClickInquires(View view){
        MainActivity.redirectActivityU(this,UserInquiryList.class);
    }
    public void ClickLogOutUser(View view){
        logOutUser(this);
    }
    public void logOutUser(Activity mainActivity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity);
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













