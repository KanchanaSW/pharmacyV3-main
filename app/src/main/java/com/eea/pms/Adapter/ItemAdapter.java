package com.eea.pms.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eea.pms.Interface.ItemClickCallback;
import com.eea.pms.ItemViewActivity;
import com.eea.pms.Model.Item;
import com.eea.pms.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<Item> itemList;
    Context context;
    SharedPreferences sharedPreference;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item=itemList.get(position);

        holder.itemId.setText("ID:"+String.valueOf(item.getItemId()));
        holder.itemName.setText(item.getItemName());
        holder.des.setText(item.getDes());
        holder.price.setText("Rs:"+String.valueOf(item.getPrice()));
        holder.quantity.setText("Avi:"+String.valueOf(item.getQuantity()));
        holder.categoryName.setText(item.getCategoryName());

        holder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("// item id => ");

            }
        });


    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemId;
        TextView itemName;
        TextView des;
        TextView price;
        TextView quantity;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemId=itemView.findViewById(R.id.item_id);
            itemName=itemView.findViewById(R.id.item_name);
            des=itemView.findViewById(R.id.item_des);
            price=itemView.findViewById(R.id.item_price);
            quantity=itemView.findViewById(R.id.item_quantity);
            categoryName=itemView.findViewById(R.id.item_category);

        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
