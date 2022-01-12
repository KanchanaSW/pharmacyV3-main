package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
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

public class AdminDash extends AppCompatActivity {
    //Initialize variables
    DrawerLayout drawerLayoutAdmin;
    RecyclerView recyclerViewOrdersList;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        //Assign variables
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Pharmacy-Orders");
        recyclerViewOrdersList=findViewById(R.id.recyclerViewOrdersList);
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
                    recyclerViewOrdersList.setAdapter(ra);
                    recyclerViewOrdersList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    recyclerViewOrdersList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                    recyclerViewOrdersList.setItemAnimator(new DefaultItemAnimator());
                }
              ///  FancyToast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }
        });
    }

    ///////////////nav drawer functions ////////////////////////////////////////////////////////////////////////////
    public void ClickMenu(View view){
        //open drawer
        openDrawer(drawerLayoutAdmin);
    }
    public static void openDrawer(DrawerLayout drawerLayoutAdmin) {
        //open drawer layout
        drawerLayoutAdmin.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayoutAdmin);
    }
    public static void closeDrawer(DrawerLayout drawerLayoutAdmin) {
        //close drawer layout
        //check
        if (drawerLayoutAdmin.isDrawerOpen(GravityCompat.START)){
            //close
            drawerLayoutAdmin.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        recreate();
    }
    public void ClickUsers(View view){
        redirectActivity(this,AdminUsersList.class);
    }
    public void ClickAccount(View view){
        redirectActivity(this,UpdateAccount.class);
    }
    public void ClickProductsAdmin(View view){
        redirectActivity(this,ItemList.class);
    }
    public void ClickRequestsAdmin(View view){
        redirectActivity(this,AdminRequestsList.class);
    }
    public void ClickInquiresAdmin(View view){
        redirectActivity(this, AdminInquiresList.class);
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
    public static void redirectActivity(Activity activity,Class aClass) {
        //inizate intent
        Intent intent=new Intent(activity,aClass);
        //set flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayoutAdmin);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}