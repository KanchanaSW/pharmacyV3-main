package com.eea.pms;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eea.pms.Storage.ContentProvider.AddNotes;
import com.eea.pms.Storage.ContentProvider.MyDatabaseHelper;
import com.eea.pms.Storage.ContentProvider.NotesAdapter;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Initialize vars
    DrawerLayout drawerLayoutUser;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayoutUser=findViewById(R.id.drawer_layout_user);
        TextView mtaHeading=findViewById(R.id.mtuHeading);
        mtaHeading.setText("Notes");
        button=findViewById(R.id.buttonCall);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("arrived");
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+94771556815"));
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    System.out.println("granted");
                    return;
                }
                startActivity(callIntent);
            }
        });

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

    ///////////    User nav functions           /////////////////////////////////////////////////////////
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
        redirectActivityU(this,UserUpdateAcc.class);
    }
    public void ClickProducts(View view){
        redirectActivityU(this,UserItemList.class);
    }
    public void ClickOrders(View view){
        redirectActivityU(this,UserOrderList.class);
    }
    public void ClickRequests(View view){
        redirectActivityU(this,UserRequestList.class);
    }
    public void ClickInquires(View view){
        redirectActivityU(this,UserInquiryList.class);
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
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
}














