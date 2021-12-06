package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eea.pms.Storage.SharedPreferenceManager;

public class MainActivity extends AppCompatActivity {

    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogout=findViewById(R.id.btnLogoutUser);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });
    }
    public void onLogout() {
        SharedPreferenceManager.getSharedPreferenceInstance(this).clear();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}