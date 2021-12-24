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
import android.widget.TextView;

import com.eea.pms.Storage.SharedPreferenceManager;

public class AdminDash extends AppCompatActivity {
    //Initialize variables
    DrawerLayout drawerLayoutAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        //Assign variables
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Home");
    }

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

}