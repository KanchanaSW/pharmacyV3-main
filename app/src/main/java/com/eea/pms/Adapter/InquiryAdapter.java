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

import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.Inquiry;
import com.eea.pms.R;
import com.eea.pms.Storage.SharedPreferenceManager;

import java.util.List;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryAdapter.ViewHolder> {
    List<Inquiry> inquiryList;
    Context context;
    SharedPreferences sharedPreference;
    private LoginResponse loginResponse;

    public InquiryAdapter(List<Inquiry> inquiryList, Context context) {
        this.inquiryList = inquiryList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(parent.getContext()).getUser();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inquiry_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inquiry inquiry=inquiryList.get(position);
        System.out.println("inquire in adpter");
        System.out.println(inquiry);
        holder.inquiry_id.setText("ID: "+String.valueOf(inquiry.getInquiryId()));
        holder.inquiry_item_name.setText(inquiry.getItemName());
        holder.inquiry_user_name.setText(inquiry.getUsername());
        holder.inquiry_question.setText("Qus: "+inquiry.getQuestion());

        if (inquiry.getAnswer() != null){
            holder.inquiry_answer.setText("Ans: "+inquiry.getAnswer());
        }else{
            holder.inquiry_answer.setText("Ans: "+"--pending--");
            holder.inquiry_answer.setTextColor(Color.BLUE);
        }

        holder.inquiry_date.setText(inquiry.getDate());
        holder.inquiry_isReplied.setText(inquiry.getRep());
    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView inquiry_id;
        TextView inquiry_item_name;
        TextView inquiry_user_name;
        TextView inquiry_question;
        TextView inquiry_answer;
        TextView inquiry_date;
        TextView inquiry_isReplied;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inquiry_id=itemView.findViewById(R.id.inquiry_id);
            inquiry_item_name=itemView.findViewById(R.id.inquiry_item_name);
            inquiry_user_name=itemView.findViewById(R.id.inquiry_user_name);
            inquiry_question=itemView.findViewById(R.id.inquiry_question);
            inquiry_answer=itemView.findViewById(R.id.inquiry_answer);
            inquiry_date=itemView.findViewById(R.id.inquiry_date);
            inquiry_isReplied=itemView.findViewById(R.id.inquiry_isReplied);
        }
    }
}
