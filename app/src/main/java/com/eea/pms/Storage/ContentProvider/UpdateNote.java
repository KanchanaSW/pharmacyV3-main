package com.eea.pms.Storage.ContentProvider;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eea.pms.LoginActivity;
import com.eea.pms.R;
import com.eea.pms.Storage.SharedPreferenceManager;

public class UpdateNote extends AppCompatActivity {
    EditText note_title,note;
    Button update_button, delete_button;

    String id, title, noteStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        //note_title=findViewById(R.id.title_noteU);
        note=findViewById(R.id.noteU);
        update_button=findViewById(R.id.update_button);
        delete_button=findViewById(R.id.delete_button);
        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateNote.this);
               // title = note_title.getText().toString().trim();
                noteStr = note.getText().toString().trim();
                myDB.updateData(id, noteStr);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    private void confirmDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete  ? ");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateNote.this);
                myDB.deleteOneRow(id);
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

    private void getAndSetIntentData() {
        if(getIntent().hasExtra("id") &&
                getIntent().hasExtra("note") ){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            //title = getIntent().getStringExtra("title");
            noteStr = getIntent().getStringExtra("note");

            //Setting Intent Data
          //  note_title.setText(title);
            note.setText(noteStr);
            Log.d("Note =>> ", noteStr);
            System.out.println(noteStr);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}