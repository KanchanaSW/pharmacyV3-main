package com.eea.pms.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.ItemList;
import com.eea.pms.Model.Item;
import com.eea.pms.R;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.ItemApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<Item> itemList;
    Context context;
    SharedPreferences sharedPreference;
    private LoginResponse loginResponse;
    Intent intent;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(parent.getContext()).getUser();

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item=itemList.get(position);
        String jwtToken="Bearer "+loginResponse.getToken();

        holder.itemId.setText("ID:"+String.valueOf(item.getItemId()));
        holder.itemName.setText(item.getItemName());
        holder.des.setText(item.getDes());
        holder.price.setText("Rs:"+String.valueOf(item.getPrice()));
        holder.quantity.setText("Avi:"+String.valueOf(item.getQuantity()));
        holder.categoryName.setText(item.getCategoryName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked =>>"+item.getItemId());
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to delete item "+item.getItemId()+" ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> deleteItem= RetrofitClient.getRetrofitClientInstance().create(ItemApi.class).deleteItem(item.getItemId(), jwtToken);
                        deleteItem.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    FancyToast.makeText(v.getContext(), " Success Item deleted", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                    intent=new Intent(v.getContext(), ItemList.class);
                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                FancyToast.makeText(v.getContext(), " Failed Error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                        });
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
        });

        holder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("// item id => "+item.getItemId());
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
        TextView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemId=itemView.findViewById(R.id.item_id);
            itemName=itemView.findViewById(R.id.item_name);
            des=itemView.findViewById(R.id.item_des);
            price=itemView.findViewById(R.id.item_price);
            quantity=itemView.findViewById(R.id.item_quantity);
            categoryName=itemView.findViewById(R.id.item_category);
            btnDelete=itemView.findViewById(R.id.ibtnToDelete);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
