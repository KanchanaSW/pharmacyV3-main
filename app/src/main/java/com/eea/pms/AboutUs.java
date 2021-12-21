package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity {

    DrawerLayout drawerLayoutAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

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

 /*   public void ClickDashboard(View view){
        AdminDash.redirectActivity(this,ItemViewActivity.class);
    }*/

    public void ClickAboutUs(View view){
        recreate();
    }
    public void ClickLogOut(View view){
        AdminDash.logOut(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AdminDash.closeDrawer(drawerLayoutAdmin);
    }
}