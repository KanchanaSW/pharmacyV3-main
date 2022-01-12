package com.eea.pms.Storage.ContentProvider;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.eea.pms.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList note_id,note;

    public NotesAdapter(Context context, Activity activity, ArrayList note_id, ArrayList note) {
        this.context = context;
        this.activity = activity;
        this.note_id = note_id;
        this.note = note;
    }

    @NonNull
    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.MyViewHolder holder, int position) {
        holder.note_id_txt.setText(String.valueOf(note_id.get(position)));
        //holder.note_title_txt.setText(String.valueOf(note_title.get(position)));
        holder.note_txt.setText(String.valueOf(note.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x= holder.getAdapterPosition();
                Intent intent = new Intent(context, UpdateNote.class);
                intent.putExtra("id", String.valueOf(note_id.get(x)));
             //   intent.putExtra("title", String.valueOf(note_title.get(x)));
                intent.putExtra("note", String.valueOf(note.get(x)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return note_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView note_id_txt,note_title_txt,note_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_id_txt = itemView.findViewById(R.id.note_id_txt);
          //  note_title_txt = itemView.findViewById(R.id.note_title_txt);
            note_txt = itemView.findViewById(R.id.note_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
          //  Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
           // mainLayout.setAnimation(translate_anim);
        }
    }
}