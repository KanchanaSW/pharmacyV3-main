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

import com.eea.pms.Adapter.UserAdapter;
import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.User;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUsersList extends AppCompatActivity {
    private LoginResponse loginResponse;
    RecyclerView recyclerViewUserList;
    DrawerLayout drawerLayoutAdmin;

    public AdminUsersList(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_list);
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        recyclerViewUserList=findViewById(R.id.recyclerViewUserList);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        getAllUsers();
        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Users-List");
    }

    private void getAllUsers() {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<User>> getAllUsers= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).getAllUsers(jwtToken);
        getAllUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                //List<User> list=response.body();
                List<User> list=new ArrayList<>();
                if( response.body() != null) {
                    for (User user : response.body()) {
                        User u=new User();
                        u.setUserId(user.getUserId());
                        u.setUsername(user.getUsername());
                        u.setEmail(user.getEmail());
                        u.setPhone(user.getPhone());
                        u.setStatus(user.getStatus());
                        u.setRole(user.getRole());
                        list.add(u);
                    }
                    UserAdapter userAdapter=new UserAdapter(list,getApplicationContext());
                    recyclerViewUserList.setAdapter(userAdapter);
                    recyclerViewUserList.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                    recyclerViewUserList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                    recyclerViewUserList.setItemAnimator(new DefaultItemAnimator());
                }
               // FancyToast.makeText(getApplicationContext(), "List is empty", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }
        });
    }

    //nav drawer functs ///////////////////////////////////////////////////////////////////////////////////////
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
       recreate();
    }
    public void ClickAccount(View view){
        AdminDash.redirectActivity(this,UpdateAccount.class);
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
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}