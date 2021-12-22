package com.eea.pms;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eea.pms.Storage.SharedPreferenceManager;

public class MainActivity extends AppCompatActivity {
    //Initialize vars
    DrawerLayout drawerLayoutUser;
    //Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayoutUser=findViewById(R.id.drawer_layout_user);

    }


    public static void openDrawerUser(DrawerLayout drawerLayoutUser) {
        drawerLayoutUser.openDrawer(GravityCompat.START);
    }

    public static void closeDrawerUser(DrawerLayout drawerLayoutUser) {
        if (drawerLayoutUser.isDrawerOpen(GravityCompat.START)){
            drawerLayoutUser.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivityU(Activity activity, Class aClass) {
        //inizate intent
        Intent intent=new Intent(activity,aClass);
        //set flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }

    public void ClickLogoUser(View view){
        closeDrawerUser(drawerLayoutUser);
    }
    public void ClickMenuUser(View view){
        openDrawerUser(drawerLayoutUser);
    }
    public void ClickHomeUser(View view){
        recreate();
    }
    public void ClickMyAccountUser(View view){
       // redirectActivityU(this,);
    }
    public void ClickProducts(View view){

    }
    public void ClickOrders(View view){

    }
    public void ClickRequests(View view){

    }
    public void ClickInquires(View view){

    }
    public void ClickLogOutUser(View view){
        logOutUser(this);
    }

    public void logOutUser(MainActivity mainActivity) {
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
        closeDrawerUser(drawerLayoutUser);
    }
}














