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

import com.eea.pms.Adapter.UserRequestAdapter;
import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.UserApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequestList extends AppCompatActivity {
    DrawerLayout drawerLayoutUser;
    RecyclerView recyclerViewUserRequestsList;
    private LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_list);
         drawerLayoutUser=findViewById(R.id.drawer_layout_user);
        TextView mtaHeading=findViewById(R.id.mtuHeading);
        mtaHeading.setText("My-Requests");
        recyclerViewUserRequestsList=findViewById(R.id.recyclerViewUserRequestsList);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        getMyRequests();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav_request);
        bottomNavigationView.setSelectedItemId(R.id.view_request_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.view_request_list:
                        return true;
                    case R.id.add_request:
                        startActivity(new Intent(getApplicationContext(),UserAddRequest.class));
                        overridePendingTransition(0,0);
                }
                return false;
            }
        });
    }

    private void getMyRequests() {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<ItemRequests>> getMyRequests= RetrofitClient.getRetrofitClientInstance().create(UserApi.class).getMyRequests(jwtToken);
        getMyRequests.enqueue(new Callback<List<ItemRequests>>() {
            @Override
            public void onResponse(Call<List<ItemRequests>> call, Response<List<ItemRequests>> response) {
                List<ItemRequests> myRequests=response.body();
                if (myRequests != null){
                    UserRequestAdapter ra=new UserRequestAdapter(myRequests,getApplicationContext());
                    recyclerViewUserRequestsList.setAdapter(ra);
                    recyclerViewUserRequestsList.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                    recyclerViewUserRequestsList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                    recyclerViewUserRequestsList.setItemAnimator(new DefaultItemAnimator());
                }else {
                    FancyToast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<List<ItemRequests>> call, Throwable t) {
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
        recreate();
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