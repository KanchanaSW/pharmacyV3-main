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

public class AdminDash extends AppCompatActivity {
    //Initialize variables
    DrawerLayout drawerLayoutAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        //Assign variables
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);

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

 /*   public void ClickDashboard(View view){
        redirectActivity(this,ItemViewActivity.class);
    }*/

    public void ClickAboutUs(View view){
        redirectActivity(this,AboutUs.class);
    }

    public void ClickLogOut(View view){
        logOut(this);
    }

    public static void logOut(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
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