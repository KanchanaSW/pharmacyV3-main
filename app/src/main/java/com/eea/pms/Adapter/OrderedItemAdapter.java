package com.eea.pms.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.OrderedItems;
import com.eea.pms.R;
import com.eea.pms.Storage.SharedPreferenceManager;

import java.util.List;

public class OrderedItemAdapter extends RecyclerView.Adapter<OrderedItemAdapter.ViewHolder> {
    List<OrderedItems> orderedItemsList;
    Context context;
    SharedPreferences sharedPreference;
    private LoginResponse loginResponse;

    public OrderedItemAdapter(List<OrderedItems> orderedItemsList, Context context) {
        this.orderedItemsList = orderedItemsList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(parent.getContext()).getUser();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_item_list,parent,false);
        return new OrderedItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderedItems oi= orderedItemsList.get(position);

        holder.ordered_item_id.setText("ID: "+String.valueOf(oi.getOrderedItemDTOId()));
        holder.ordered_item_name.setText(oi.getItemName());
        holder.o_i_quantity.setText("Quantity: "+String.valueOf(oi.getQuantity()));
        holder.o_i_price.setText("Rs. "+String.valueOf(oi.getTotal()));
        holder.o_i_date.setText(oi.getDate());
    }

    @Override
    public int getItemCount() {
        return orderedItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ordered_item_id;
        TextView ordered_item_name;
        TextView o_i_quantity;
        TextView o_i_price;
        TextView o_i_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ordered_item_id=itemView.findViewById(R.id.ordered_item_id);
            ordered_item_name=itemView.findViewById(R.id.ordered_item_name);
            o_i_quantity=itemView.findViewById(R.id.o_i_quantity);
            o_i_price=itemView.findViewById(R.id.o_i_price);
            o_i_date=itemView.findViewById(R.id.o_i_date);
        }
    }
}
