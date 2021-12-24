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

import com.eea.pms.Adapter.RequestsAdapter;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRequestsList extends AppCompatActivity {
    private LoginResponse loginResponse;
    DrawerLayout drawerLayoutAdmin;
    RecyclerView recyclerViewRequestsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requests_list);
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Item Requests List");
        recyclerViewRequestsList=findViewById(R.id.recyclerViewRequestsList);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        getAllRequests();
    }

    private void getAllRequests() {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<ItemRequests>> getAll= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).requestsList(jwtToken);
        getAll.enqueue(new Callback<List<ItemRequests>>() {
            @Override
            public void onResponse(Call<List<ItemRequests>> call, Response<List<ItemRequests>> response) {
                List<ItemRequests> list=new ArrayList<>();
                if (response.body() != null){
                    for (ItemRequests ir : response.body()){
                        ItemRequests i=new ItemRequests();
                        i.setItemRequestsId(ir.getItemRequestsId());
                        i.setNewItemName(ir.getNewItemName());
                        i.setNote(ir.getNote());
                        i.setUserId(ir.getUserId());
                        i.setUsername(ir.getUsername());
                        i.setStatus(ir.getStatus());
                        list.add(i);
                    }
                    RequestsAdapter ra=new RequestsAdapter(list,getApplicationContext());
                    recyclerViewRequestsList.setAdapter(ra);
                    recyclerViewRequestsList.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                    recyclerViewRequestsList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                    recyclerViewRequestsList.setItemAnimator(new DefaultItemAnimator());
                }
            }

            @Override
            public void onFailure(Call<List<ItemRequests>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }
        });
    }

    // navigation functions ///////////////////////////////////////////////////////////////////////////////
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
        AdminDash.redirectActivity(this,UpdateAccount.class);
    }
    public void ClickProductsAdmin(View view){
        AdminDash.redirectActivity(this,ItemList.class);
    }
    public void ClickRequestsAdmin(View view){
      recreate();
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
}