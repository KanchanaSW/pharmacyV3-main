package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eea.pms.Storage.SharedPreferenceManager;

public class MainActivityAdmin extends AppCompatActivity {

   Button btnLogout,btnItems,btnDrawer;
   Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        btnLogout=findViewById(R.id.btnLogout);
        btnItems=findViewById(R.id.btnItemsA);
        btnDrawer=findViewById(R.id.btnDrawer);

       btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });
       btnItems.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),ItemViewActivity.class));
           }
       });
        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), AdminDash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
    public void onLogout() {
        SharedPreferenceManager.getSharedPreferenceInstance(this).clear();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}