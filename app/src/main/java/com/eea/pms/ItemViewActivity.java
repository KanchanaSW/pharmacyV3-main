package com.eea.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.eea.pms.Adapter.ItemAdapter;
import com.eea.pms.DTO.Responses.LoginResponse;
import com.eea.pms.DTO.Responses.MessageResponse;
import com.eea.pms.Model.Category;
import com.eea.pms.Model.Item;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.ItemApi;
import com.eea.pms.Storage.SharedPreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemViewActivity extends AppCompatActivity implements View.OnClickListener {
    //Initialize variables
    EditText etItemName, etDes, etQuantity, etPrice;
    Spinner spinnerCategory;
    String selectedCategory="";
    TextInputLayout itemName_error,itemDes_error,itemCategory_error,itemQuantity_error,itemPrice_error;
    boolean isitemNameValid,isitemDesValid,isitemCategoryValid,isitemQuantityValid,isitemPriceValid;
    Button btnAddItem;
    Intent intent;
    RecyclerView recyclerViewAI;
    private List<Item> itemList = new ArrayList<>();
    private LoginResponse loginResponse;
    private ItemAdapter itemAdapter;

    public ItemViewActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        //assigne variables
        itemName_error=findViewById(R.id.itemName_error);
        itemDes_error=findViewById(R.id.itemDes_error);
        itemCategory_error=findViewById(R.id.itemCategory_error);
        itemQuantity_error=findViewById(R.id.itemQuantity_error);
        itemPrice_error=findViewById(R.id.itemPrice_error);

        etItemName=findViewById(R.id.etItemName);
        etDes=findViewById(R.id.etDes);
       // etCategory=findViewById(R.id.etCategory);
        etQuantity=findViewById(R.id.etQuantity);
        etPrice=findViewById(R.id.etPrice);
        btnAddItem=findViewById(R.id.btnAddItem);
        recyclerViewAI = findViewById(R.id.recyclerViewAI);

        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();

        spinnerCategory=findViewById(R.id.spinnerCategory);
        getItemL();
        getCatgList();
        //String[] items = new String[]{"1", "2", "three"};
        //List<Category> test=new ArrayList<>();


        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnAddItem();

            }
        });

    }

    private void onBtnAddItem() {
        String iName=etItemName.getText().toString();
        String iDes=etDes.getText().toString();
        String iCatName=selectedCategory;
        String iQuant=etQuantity.getText().toString();
        String iPrice=etPrice.getText().toString();

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
            Item newItem=new Item(iName,iDes,iCatName,Integer.parseInt(iQuant),Double.parseDouble(iPrice));

            Call<Item> addItem=RetrofitClient.getRetrofitClientInstance().create(ItemApi.class).addItem(newItem,jwtToken);
            addItem.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    if (response.isSuccessful()){
                        FancyToast.makeText(getApplicationContext(), " Success", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        finish();
                        startActivity(getIntent());
                    }else {
                        FancyToast.makeText(getApplicationContext(), " Error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    FancyToast.makeText(getApplicationContext(), " Failed Error", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                }
            });
        }

    }

    private void getCatgList(){
        String jwtToken="Bearer "+loginResponse.getToken();
        Call<List<Category>> getCategoryList=RetrofitClient.getRetrofitClientInstance().create(ItemApi.class).getCategoryList(jwtToken);
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

                spinnerCategory.setAdapter(adapter);
                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void getItemL(){


        Call<List<Item>> getItemsList= RetrofitClient.getRetrofitClientInstance().create(ItemApi.class).getAllItems(loginResponse.getToken());
        getItemsList.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> list=response.body();
                ItemAdapter adapter=new ItemAdapter(list,getApplicationContext());
                recyclerViewAI.setAdapter(adapter);
                recyclerViewAI.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                recyclerViewAI.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                recyclerViewAI.setItemAnimator(new DefaultItemAnimator());

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//
//    }

    @Override
    public void onClick(View v) {

    }
}