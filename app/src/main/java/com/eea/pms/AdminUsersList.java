package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eea.pms.Storage.SharedPreferenceManager;

public class AdminUsersList extends AppCompatActivity {

    DrawerLayout drawerLayoutAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_list);
        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
    }
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
       recreate();
    }
    public void ClickAccount(View view){
        AdminDash.redirectActivity(this,UpdateAccount.class);
    }
    public void ClickProductsAdmin(View view){
        AdminDash.redirectActivity(this,ItemViewActivity.class);
    }
    public void ClickRequestsAdmin(View view){
        AdminDash.redirectActivity(this,AdminRequestsList.class);
    }

    public void ClickInquiresAdmin(View view){
        AdminDash.redirectActivity(this,Inquires.class);
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
                //activity.finishAffinity();
                //System.exit(0);
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
        AdminDash.closeDrawer(drawerLayoutAdmin);
    }
}