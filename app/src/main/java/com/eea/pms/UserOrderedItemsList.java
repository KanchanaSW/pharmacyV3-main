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

import com.eea.pms.Adapter.OrderedItemAdapter;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.OrderedItems;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderedItemsList extends AppCompatActivity {
    private LoginResponse loginResponse;
    DrawerLayout drawerLayoutUser;
    RecyclerView recyclerViewOrderedItemsListUser;
    Integer orderedItemDTOId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ordered_items_list);

        //get the id form order adapter
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            orderedItemDTOId=bundle.getInt("orderedItemDTOId");
        }else{
            orderedItemDTOId=0;
        }
        drawerLayoutUser=findViewById(R.id.drawer_layout_user);
        TextView mtaHeading=findViewById(R.id.mtuHeading);
        mtaHeading.setText("Ordered Items");
        recyclerViewOrderedItemsListUser=findViewById(R.id.recyclerViewOrderedItemsListUser);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        getAllOrderedItems(orderedItemDTOId);
    }

    private void getAllOrderedItems(Integer orderedItemDTOId) {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<OrderedItems>> getOrderedItems= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).getOrderedItems(orderedItemDTOId,jwtToken);
        getOrderedItems.enqueue(new Callback<List<OrderedItems>>() {
            @Override
            public void onResponse(Call<List<OrderedItems>> call, Response<List<OrderedItems>> response) {
                List<OrderedItems> oIList=response.body();
                OrderedItemAdapter ra=new OrderedItemAdapter(oIList,getApplicationContext());
                recyclerViewOrderedItemsListUser.setAdapter(ra);
                recyclerViewOrderedItemsListUser.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                recyclerViewOrderedItemsListUser.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                recyclerViewOrderedItemsListUser.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<List<OrderedItems>> call, Throwable t) {
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