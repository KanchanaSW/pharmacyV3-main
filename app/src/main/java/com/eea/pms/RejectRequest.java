package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RejectRequest extends AppCompatActivity {
    Integer itemRequestId;
    DrawerLayout drawerLayoutAdmin;
    EditText etItemNameRI, etDesRI;
    TextInputLayout itemName_error,itemDes_error;
    Button btnRejectItem;
    LoginResponse loginResponse;
    boolean isitemDesValid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_request);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            itemRequestId=bundle.getInt("requestId");
        }else{
            itemRequestId=0;
        }
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        itemName_error=findViewById(R.id.itemName_error);
        itemDes_error=findViewById(R.id.itemDes_error);
        getDeeds(itemRequestId);
        etItemNameRI=findViewById(R.id.etItemNameRI);etItemNameRI.setEnabled(false);
        etDesRI=findViewById(R.id.etDesRI);
        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Reject request");
        btnRejectItem=findViewById(R.id.btnRejectItem);
        btnRejectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRequest(itemRequestId);
            }
        });
    }
    private void getDeeds(Integer itemRequestId) {
        System.out.println(itemRequestId);
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<ItemRequests> getMI=RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).getMI(itemRequestId,jwtToken);
        getMI.enqueue(new Callback<ItemRequests>() {
            @Override
            public void onResponse(Call<ItemRequests> call, Response<ItemRequests> response) {
                ItemRequests ir=response.body();
                System.out.println(ir);
                if (ir != null){
                    etItemNameRI.setText(ir.getNewItemName());
                    etDesRI.setText(ir.getNote());
                }
            }

            @Override
            public void onFailure(Call<ItemRequests> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }
        });
    }

    private void rejectRequest(Integer itemRequestId) {
        System.out.println(itemRequestId);
        String note=etDesRI.getText().toString();

        if (note.isEmpty()){
            itemDes_error.setError("Please add note");
            isitemDesValid=false;
        }else if (note.length()>255){
            itemDes_error.setError("Too long");
            isitemDesValid=false;
        }else{
            isitemDesValid=true;
            itemDes_error.setErrorEnabled(false);
        }
        if (isitemDesValid){
            String jwtToken="Bearer "+loginResponse.getToken();
            ItemRequests ir = new ItemRequests(itemRequestId,note);
            Call<ItemRequests> reject= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).rejectReq(ir,jwtToken);
            reject.enqueue(new Callback<ItemRequests>() {
                @Override
                public void onResponse(Call<ItemRequests> call, Response<ItemRequests> response) {
                    startActivity(new Intent(getApplicationContext(),AdminRequestsList.class));
                    FancyToast.makeText(getApplicationContext(), " Rejected Success", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                }

                @Override
                public void onFailure(Call<ItemRequests> call, Throwable t) {
                    FancyToast.makeText(getApplicationContext(), " Error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            });
        }

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