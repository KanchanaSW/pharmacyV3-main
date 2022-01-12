package com.eea.pms.Storage.ContentProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eea.pms.R;

public class AddNotes extends AppCompatActivity {
    EditText note_title, note;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
       // note_title=findViewById(R.id.noteTitleAdd);
        note=findViewById(R.id.noteAdd);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddNotes.this);
                myDB.addNote(note.getText().toString().trim());
            }
        });
    }
}
//note_title.getText().toString().trim(),