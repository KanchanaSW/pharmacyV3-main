package com.eea.pms.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.R;
import com.eea.pms.Storage.SharedPreferenceManager;

import java.util.List;

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.ViewHolder> {
    List<ItemRequests> requestList;
    Context context;
    SharedPreferences sharedPreference;
    private LoginResponse loginResponse;

    public UserRequestAdapter(List<ItemRequests> requestList, Context context) {
        this.requestList = requestList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(parent.getContext()).getUser();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_requsts_list,parent,false);
        return new UserRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemRequests itemRequests=requestList.get(position);

        holder.user_request_id.setText("ID: "+String.valueOf(itemRequests.getItemRequestsId()));
        holder.user_request_item_name.setText(itemRequests.getNewItemName());
        if (itemRequests.getStatus().equals("pending")){
            holder.user_request_status.setText("---"+String.valueOf(itemRequests.getStatus())+"---");
            holder.user_request_status.setTextColor(Color.BLUE);
        }else{
            holder.user_request_status.setText(String.valueOf(itemRequests.getStatus()));
        }
        holder.user_request_note.setText(itemRequests.getNote());

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_request_id;
        TextView user_request_item_name;
        TextView user_request_status;
        TextView user_request_note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_request_id=itemView.findViewById(R.id.user_request_id);
            user_request_item_name=itemView.findViewById(R.id.user_request_item_name);
            user_request_status=itemView.findViewById(R.id.user_request_status);
            user_request_note=itemView.findViewById(R.id.user_request_note);
        }
    }
}
