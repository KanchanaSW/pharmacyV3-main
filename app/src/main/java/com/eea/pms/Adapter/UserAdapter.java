package com.eea.pms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.Item;
import com.eea.pms.Model.User;
import com.eea.pms.R;
import com.eea.pms.Storage.SharedPreferenceManager;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> userList;
    Context context;
    SharedPreferences sharedPreference;
    private LoginResponse loginResponse;
    Intent intent;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(parent.getContext()).getUser();
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user= userList.get(position);
        String jwtToken="Bearer "+loginResponse.getToken();

        holder.userId.setText("ID: "+String.valueOf(user.getUserId()));
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());
        holder.phone.setText(user.getPhone());
        holder.status.setText(user.getStatus());
        holder.role.setText(String.valueOf(user.getRole()));


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userId;
        TextView username;
        TextView email;
        TextView phone;
        TextView status;
        TextView role;
        //TextView btnDeleteUser;

        public ViewHolder(@NonNull View userView) {
            super(userView);
            userId=itemView.findViewById(R.id.user_id);
            username=itemView.findViewById(R.id.user_name);
            email=itemView.findViewById(R.id.user_email);
            phone=itemView.findViewById(R.id.user_phone);
            status=itemView.findViewById(R.id.user_status);
            role=itemView.findViewById(R.id.user_role);
            //btnDeleteUser=itemView.findViewById(R.id.ibtnToDeleteUser);
        }
    }
}
