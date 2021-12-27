package com.eea.pms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.DTO.Responses.MessageResponse;
import com.eea.pms.Model.Inquiry;
import com.eea.pms.Model.Item;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.ItemApi;
import com.eea.pms.RetrofitInterface.UserApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAddInquiry extends AppCompatActivity {
    DrawerLayout drawerLayoutUser;
    private LoginResponse loginResponse;
    RecyclerView recyclerViewInquiryListUser;
    EditText etInquiry,etItemNameInquiry;
    Button btnAddInquiry;
    TextInputLayout inquiry_error,itemName_error;
    Integer itemId;
    Boolean isInquiryValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_inquiry);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        drawerLayoutUser=findViewById(R.id.drawer_layout_user);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            itemId=bundle.getInt("itemId");
        }else{
            itemId=0;
        }
        System.out.println(itemId);

        TextView mtaHeading=findViewById(R.id.mtuHeading);
        mtaHeading.setText("Add new Inquiry");
        recyclerViewInquiryListUser=findViewById(R.id.recyclerViewInquiryListUser);
        etInquiry=findViewById(R.id.etInquiry);
        etItemNameInquiry=findViewById(R.id.etItemNameInquiry);etItemNameInquiry.setEnabled(false);
        btnAddInquiry=findViewById(R.id.btnAddInquiry);
        inquiry_error=findViewById(R.id.inquiry_error);
        itemName_error=findViewById(R.id.itemName_error);

        getDetails(itemId);

        btnAddInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInquiry(itemId);
            }
        });


    }

    private void addInquiry(Integer itemId) {
        String jwtToken="Bearer "+loginResponse.getToken();
        String inquiry =etInquiry.getText().toString();

        if (inquiry.isEmpty()){
            inquiry_error.setError("Please enter details for the inquiry.");
            isInquiryValid=false;
        }else if (inquiry.length()>200){
            inquiry_error.setError("Too many characters");
            isInquiryValid=false;
        }else if (inquiry.length()<15){
            inquiry_error.setError("Too few words.");
            isInquiryValid=false;
        }else {
            isInquiryValid=true;
            inquiry_error.setErrorEnabled(false);
        }
        if (isInquiryValid){
            Inquiry newInquiry=new Inquiry(inquiry);
            Call<MessageResponse> addInquiry=RetrofitClient.getRetrofitClientInstance().create(UserApi.class).addInquiry(itemId,newInquiry,jwtToken);
            addInquiry.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    FancyToast.makeText(getApplicationContext(), " Success", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    startActivity(new Intent(getApplicationContext(),UserInquiryList.class));
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    FancyToast.makeText(getApplicationContext(), " Failed", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            });
        }


    }

    private void getDetails(Integer itemId) {
        System.out.println(itemId);
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<Item> getItem= RetrofitClient.getRetrofitClientInstance().create(ItemApi.class).getItem(itemId,jwtToken);
        getItem.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item= response.body();
                if (item != null){
                    etItemNameInquiry.setText(item.getItemName());
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
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