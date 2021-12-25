package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.Model.Category;
import com.eea.pms.Model.Item;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.RetrofitInterface.ItemApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageItemRequest extends AppCompatActivity {
    DrawerLayout drawerLayoutAdmin;
    EditText etItemNameMI, etDesMI, etQuantityMI, etPriceMI;
    Spinner spinnerCategoryMI;
    String selectedCategory="";
    TextInputLayout itemName_error,itemDes_error,itemCategory_error,itemQuantity_error,itemPrice_error;
    boolean isitemNameValid,isitemDesValid,isitemCategoryValid,isitemQuantityValid,isitemPriceValid;
    Button btnAddItemMI;
    LoginResponse loginResponse;
    Integer itemRequestId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_item_request);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            itemRequestId=bundle.getInt("requestId");
        }else{
            itemRequestId=0;
        }

        drawerLayoutAdmin=findViewById(R.id.drawer_layout_admin);
        itemName_error=findViewById(R.id.itemName_error);
        itemDes_error=findViewById(R.id.itemDes_error);
        itemCategory_error=findViewById(R.id.itemCategory_error);
        itemQuantity_error=findViewById(R.id.itemQuantity_error);
        itemPrice_error=findViewById(R.id.itemPrice_error);

        getDetails(itemRequestId);

        etItemNameMI=findViewById(R.id.etItemNameMI);
        etDesMI=findViewById(R.id.etDesMI);
        etQuantityMI=findViewById(R.id.etQuantityMI);
        etPriceMI=findViewById(R.id.etPriceMI);
        btnAddItemMI=findViewById(R.id.btnAddItemMI);
        spinnerCategoryMI=findViewById(R.id.spinnerCategoryMI);
        TextView mtaHeading=findViewById(R.id.mtaHeading);
        mtaHeading.setText("Manage new item");
        getCatgList();
        btnAddItemMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItemAdd(itemRequestId);
            }
        });
    }

    private void getDetails(Integer itemRequestId) {
        System.out.println(itemRequestId);
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<ItemRequests> getMI=RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).getMI(itemRequestId,jwtToken);
        getMI.enqueue(new Callback<ItemRequests>() {
            @Override
            public void onResponse(Call<ItemRequests> call, Response<ItemRequests> response) {
                ItemRequests ir=response.body();
                System.out.println(ir);
                if (ir != null){
                    etItemNameMI.setText(ir.getNewItemName());
                    etDesMI.setText(ir.getNote());
                }
            }

            @Override
            public void onFailure(Call<ItemRequests> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }
        });
    }

    private void manageItemAdd(Integer itemRequestId) {
        String iName=etItemNameMI.getText().toString();
        String iDes=etDesMI.getText().toString();
        String iCatName=selectedCategory;
        String iQuant=etQuantityMI.getText().toString();
        String iPrice=etPriceMI.getText().toString();

        if (iName.isEmpty()){
            itemName_error.setError("Please enter the item name");
            isitemNameValid=false;
        }else {
            isitemNameValid=true;
            itemName_error.setErrorEnabled(false);
        }
        if (iDes.isEmpty()){
            itemDes_error.setError("Please add note");
            isitemDesValid=false;
        }else if (iDes.length()>80){
            itemDes_error.setError("Too long");
            isitemDesValid=false;
        }else{
            isitemDesValid=true;
            itemDes_error.setErrorEnabled(false);
        }
        if (iCatName.isEmpty()){
            itemCategory_error.setError("Required");
            isitemCategoryValid=false;
        }else {
            isitemCategoryValid=true;
            itemCategory_error.setErrorEnabled(false);
        }
        if (iQuant.isEmpty()){
            itemQuantity_error.setError("Required");
            isitemQuantityValid=false;
        }else {
            isitemQuantityValid=true;
            itemQuantity_error.setErrorEnabled(false);
        }
        if (iPrice.isEmpty()){
            itemPrice_error.setError("Required");
            isitemPriceValid=false;
        }else {
            isitemPriceValid=true;
            itemPrice_error.setErrorEnabled(false);
        }
        if (isitemNameValid && isitemDesValid && isitemCategoryValid && isitemQuantityValid && isitemPriceValid){
            String jwtToken="Bearer "+loginResponse.getToken();
            Item newItem=new Item(itemRequestId,iName,iDes,iCatName,Integer.parseInt(iQuant),Double.parseDouble(iPrice));

            Call<Item> addItem= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).manageRequest(newItem,jwtToken);
            addItem.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    FancyToast.makeText(getApplicationContext(), " Success", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    startActivity(new Intent(getApplicationContext(),AdminRequestsList.class));
                    //finish();
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    FancyToast.makeText(getApplicationContext(), " Item already found", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                }
            });
        }
    }

    private void getCatgList(){
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<Category>> getCategoryList= RetrofitClient.getRetrofitClientInstance().create(ItemApi.class).getCategoryList(jwtToken);
        getCategoryList.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> catList=response.body();
                List<String> catName=new ArrayList<>();

                if (catList != null) {
                    for (int k=0;k<catList.size();k++){
                        catName.add(catList.get(k).getCategory());
                    }
                }

                ArrayAdapter<String> adapter = new
                        ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, catName);

                spinnerCategoryMI.setAdapter(adapter);
                spinnerCategoryMI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedCategory= catName.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        selectedCategory=null;
                        FancyToast.makeText(getApplicationContext(), "No category selected", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Category List error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }
        });
    }


    /// nav drawer functions//////////////////////////////////////////////////////////////////////////////////
    public void ClickMenu(View view){
        AdminDash.openDrawer(drawerLayoutAdmin);
    }
    public void ClickLogo(View view){
        AdminDash.closeDrawer(drawerLayoutAdmin);
    }
    public void ClickHome(View view){
        AdminDash.redirectActivity(this,AdminDash.class);
    }
    public void ClickUsers(View view){
        AdminDash.redirectActivity(this,AdminUsersList.class);
    }
    public void ClickAccount(View view){
        AdminDash.redirectActivity(this,UpdateAccount.class);
    }
    public void ClickProductsAdmin(View view){
        AdminDash.redirectActivity(this,ItemList.class);
    }
    public void ClickRequestsAdmin(View view){
        AdminDash.redirectActivity(this,AdminRequestsList.class);
    }
    public void ClickInquiresAdmin(View view){
        AdminDash.redirectActivity(this,AdminInquiresList.class);
    }
    public void ClickLogOut(View view){
        logOut(this);
    }
    public void logOut(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).clear();
                activity.finishAffinity();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
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
    @Override
    protected void onPause() {
        super.onPause();
        AdminDash.closeDrawer(drawerLayoutAdmin);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

}