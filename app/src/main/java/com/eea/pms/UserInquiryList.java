package com.eea.pms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eea.pms.Adapter.InquiryAdapter;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.Inquiry;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.RetrofitInterface.UserApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInquiryList extends AppCompatActivity {
    DrawerLayout drawerLayoutUser;
    private LoginResponse loginResponse;
    RecyclerView recyclerViewInquiryListUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inquiry_list);
       drawerLayoutUser=findViewById(R.id.drawer_layout_user);
       TextView mtaHeading=findViewById(R.id.mtuHeading);
       mtaHeading.setText("My Inquires");
        recyclerViewInquiryListUser=findViewById(R.id.recyclerViewInquiryListUser);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        getMyInquires();


    }

    private void getMyInquires() {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<Inquiry>> getInquires= RetrofitClient.getRetrofitClientInstance().create(UserApi.class).getMyInquires(jwtToken);
        getInquires.enqueue(new Callback<List<Inquiry>>() {
            @Override
            public void onResponse(Call<List<Inquiry>> call, Response<List<Inquiry>> response) {
                List<Inquiry> list=new ArrayList<>();
                if (response.body() != null){
                    for (Inquiry ir : response.body()){
                        Inquiry i=new Inquiry();
                        i.setInquiryId(ir.getInquiryId());
                        i.setUsername(ir.getUsername());
                        i.setItemName(ir.getItemName());
                        i.setQuestion(ir.getQuestion());
                        i.setAnswer(ir.getAnswer());
                        i.setDate(ir.getDate());
                        i.setRep(ir.getRep());
                        list.add(i);
                    }
                    System.out.println("Inquiry list my ==>>");
                    System.out.println(list);
                    InquiryAdapter ra=new InquiryAdapter(list,getApplicationContext());
                    recyclerViewInquiryListUser.setAdapter(ra);
                    recyclerViewInquiryListUser.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                    recyclerViewInquiryListUser.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                    recyclerViewInquiryListUser.setItemAnimator(new DefaultItemAnimator());
                }
            }

            @Override
            public void onFailure(Call<List<Inquiry>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
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
        recreate();
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