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
import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.OrderedItems;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderedItemsList extends AppCompatActivity {
    private LoginResponse loginResponse;
    DrawerLayout drawerLayoutAdmin;
    RecyclerView recyclerViewOrderedItemsList;
    Integer orderedItemDTOId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_items_list);
        //get the id form order adapter
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            orderedItemDTOId=bundle.getInt("orderedItemDTOId");
        }else{
            orderedItemDTOId=0;
        }
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Ordered-Items");
        recyclerViewOrderedItemsList=findViewById(R.id.recyclerViewOrderedItemsList);
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
                recyclerViewOrderedItemsList.setAdapter(ra);
                recyclerViewOrderedItemsList.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                recyclerViewOrderedItemsList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                recyclerViewOrderedItemsList.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<List<OrderedItems>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }


    /// nav drawer functions//////////////////////////////////////////////////////////////////////////////////
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
        AdminDash.redirectActivity(this,AdminRequestsList.class);
    }
    public void ClickInquiresAdmin(View view){
        AdminDash.redirectActivity(this,AdminInquiresList.class);
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
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

}