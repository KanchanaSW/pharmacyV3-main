package com.eea.pms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eea.pms.AdminDash;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.LoginActivity;
import com.eea.pms.ManageItemRequest;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.R;
import com.eea.pms.Storage.SharedPreferenceManager;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {
    List<ItemRequests> requestList;
    Context context;
    SharedPreferences sharedPreference;
    private LoginResponse loginResponse;

    public RequestsAdapter(List<ItemRequests> requestList, Context context) {
        this.requestList = requestList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public RequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(parent.getContext()).getUser();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_list,parent,false);
        return new RequestsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.ViewHolder holder, int position) {
        ItemRequests itemRequests=requestList.get(position);

        holder.requestId.setText("ID: "+String.valueOf(itemRequests.getItemRequestsId()));
        holder.itemName.setText(itemRequests.getNewItemName());
        holder.note.setText(itemRequests.getNote());
        holder.userId.setText(String.valueOf(itemRequests.getUserId()));
        holder.userName.setText(itemRequests.getUsername());
        if (itemRequests.getStatus().equals("pending")){
            holder.requestStatus.setText(String.valueOf(itemRequests.getStatus()));
            holder.requestStatus.setTextColor(Color.RED);
        }else{
            holder.requestStatus.setText(String.valueOf(itemRequests.getStatus()));
        }


    }



    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView requestId;
        TextView itemName;
        TextView note;
        TextView userId;
        TextView userName;
        TextView requestStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            requestId=itemView.findViewById(R.id.request_id);
            itemName=itemView.findViewById(R.id.request_item_name);
            note=itemView.findViewById(R.id.request_note);
            userId=itemView.findViewById(R.id.request_user_id);
            userName=itemView.findViewById(R.id.request_user_name);
            requestStatus=itemView.findViewById(R.id.request_status);

        }

        @Override
        public void onClick(View v) {
            int po=getAdapterPosition();
            ItemRequests ir=requestList.get(po);
            System.out.println(ir.getItemRequestsId());
            context.startActivity(new Intent(v.getContext(), ManageItemRequest.class).putExtra("requestId",ir.getItemRequestsId()));
        }
    }
}
