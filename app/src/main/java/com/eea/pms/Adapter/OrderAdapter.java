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
import com.eea.pms.ManageItemRequest;
import com.eea.pms.Model.Inquiry;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.Model.Order;
import com.eea.pms.OrderedItemsList;
import com.eea.pms.R;
import com.eea.pms.Storage.SharedPreferenceManager;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<Order> orderList;
    Context context;
    SharedPreferences sharedPreference;
    private LoginResponse loginResponse;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(parent.getContext()).getUser();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list,parent,false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order= orderList.get(position);
        holder.order_id.setText("ID: "+String.valueOf(order.getOrdersDTOId()));
        holder.cus_name.setText(order.getCusName());
        holder.order_status.setText(order.getStatus());
        holder.address.setText("Address: "+order.getAddress());
        holder.order_date.setText(order.getDate());
        holder.city.setText(order.getCity());
        holder.total.setText("Total: "+String.valueOf(order.getTotal()));
        holder.pharmacy_name.setText("Pharmacist: "+order.getUsername());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView order_id;
        TextView cus_name;
        TextView order_status;
        TextView address;
        TextView order_date;
        TextView city;
        TextView total;
        TextView pharmacy_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            order_id=itemView.findViewById(R.id.order_id);
            cus_name=itemView.findViewById(R.id.cus_name);
            order_status=itemView.findViewById(R.id.order_status);
            address=itemView.findViewById(R.id.address);
            order_date=itemView.findViewById(R.id.order_date);
            city=itemView.findViewById(R.id.city);
            total=itemView.findViewById(R.id.total);
            pharmacy_name=itemView.findViewById(R.id.pharmacy_name);

        }

        @Override
        public void onClick(View v) {
            int po=getAdapterPosition();
            System.out.println(po);
            Order order= orderList.get(po);
            context.startActivity(new Intent(v.getContext(), OrderedItemsList.class).putExtra("orderedItemDTOId",order.getOrdersDTOId()));
        }
    }
}
