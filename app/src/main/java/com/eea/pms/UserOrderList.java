package com.eea.pms;

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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eea.pms.Adapter.OrderAdapter;
import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.Order;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderList extends AppCompatActivity {
    DrawerLayout drawerLayoutUser;
    RecyclerView recyclerViewOrdersListUser;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);
        drawerLayoutUser=findViewById(R.id.drawer_layout_user);
        TextView mtaHeading=findViewById(R.id.mtuHeading);
        mtaHeading.setText("My-Orders");
        recyclerViewOrdersListUser=findViewById(R.id.recyclerViewOrdersListUser);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        getAllOrders();

    }

    private void getAllOrders() {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<Order>> getAll= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).getAllOrders(jwtToken);
        getAll.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> list = new ArrayList<>();
                if (response.body() != null) {
                    for (Order orders : response.body()) {
                        Order od = new Order();
                        od.setOrdersDTOId(orders.getOrdersDTOId());
                        od.setCusName(orders.getCusName());
                        od.setCity(orders.getCity());
                        od.setAddress(orders.getAddress());
                        od.setDate(orders.getDate());
                        od.setTotal(orders.getTotal());
                        od.setStatus(orders.getStatus());
                        od.setUsername(orders.getUsername());
                        list.add(od);
                    }
                    System.out.println(list);
                    OrderAdapter ra = new OrderAdapter(list, getApplicationContext());
                    recyclerViewOrdersListUser.setAdapter(ra);
                    recyclerViewOrdersListUser.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    recyclerViewOrdersListUser.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                    recyclerViewOrdersListUser.setItemAnimator(new DefaultItemAnimator());
                }else{
                    FancyToast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
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
        recreate();
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