package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.eea.pms.Adapter.ItemAdapter;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.Item;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.ItemApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemList extends AppCompatActivity {

    DrawerLayout drawerLayoutAdmin;
    private LoginResponse loginResponse;
    RecyclerView recyclerViewAI;
    ImageButton btnAddNewItem;

    public ItemList() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        recyclerViewAI = findViewById(R.id.recyclerViewAI);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        //Initiate items list
        getItemL();
        btnAddNewItem=findViewById(R.id.addNewItem);
        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
    }

    //drawer functions  ===/////==
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
        recreate();
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
    //               ===/////==

    public void getItemL(){
        Call<List<Item>> getItemsList= RetrofitClient.getRetrofitClientInstance().create(ItemApi.class).getAllItems(loginResponse.getToken());
        getItemsList.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> list=response.body();
                ItemAdapter adapter=new ItemAdapter(list,getApplicationContext());
                recyclerViewAI.setAdapter(adapter);
                recyclerViewAI.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                recyclerViewAI.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                recyclerViewAI.setItemAnimator(new DefaultItemAnimator());
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }


    private void addNewItem() {
        startActivity(new Intent(getApplicationContext(),AdminAddItemAc.class));
    }


}