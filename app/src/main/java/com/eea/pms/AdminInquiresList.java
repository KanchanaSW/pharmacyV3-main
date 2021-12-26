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

import com.eea.pms.Adapter.InquiryAdapter;
import com.eea.pms.Adapter.RequestsAdapter;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.Inquiry;
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

public class AdminInquiresList extends AppCompatActivity {
    private LoginResponse loginResponse;
    RecyclerView recyclerViewInquiryList;
    DrawerLayout drawerLayoutAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inquires_list);

        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Inquires List");
        recyclerViewInquiryList=findViewById(R.id.recyclerViewInquiryList);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        getAllInquires();
    }

    private void getAllInquires() {
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<Inquiry>> getInquires= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).getAllInquires(jwtToken);
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

                InquiryAdapter ra=new InquiryAdapter(list,getApplicationContext());
                recyclerViewInquiryList.setAdapter(ra);
                recyclerViewInquiryList.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                recyclerViewInquiryList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                recyclerViewInquiryList.setItemAnimator(new DefaultItemAnimator());
            }
            }

            @Override
            public void onFailure(Call<List<Inquiry>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }


    //nav functions ///////////////////////////////////////////////////////////////////////////////////////
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
        recreate();
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

}