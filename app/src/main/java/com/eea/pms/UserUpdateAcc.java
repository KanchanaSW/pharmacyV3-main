package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eea.pms.Storage.SharedPreferenceManager;

public class UserUpdateAcc extends AppCompatActivity {
 DrawerLayout drawerLayoutUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_acc);
           drawerLayoutUser=findViewById(R.id.drawer_layout_user);
        TextView mtaHeading=findViewById(R.id.mtuHeading);
        mtaHeading.setText("My-Account");
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
       recreate();
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