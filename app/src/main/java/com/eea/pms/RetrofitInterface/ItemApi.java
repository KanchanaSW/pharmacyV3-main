package com.eea.pms.RetrofitInterface;

import com.eea.pms.DTO.Responses.MessageResponse;
import com.eea.pms.Model.Category;
import com.eea.pms.Model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ItemApi {
    @POST("api/items/admin/new-item")
    Call<Item> addItem(@Body Item item, @Header("Authorization") String token);

    @GET("api/items/itemAll")
    Call<List<Item>> getAllItems(@Header("Authorization") String token);

    @GET("api/category/all")
    Call<List<Category>> getCategoryList(@Header("Authorization") String token);

    @DELETE("api/items/deleteItem/{itemId}")
    Call<Void> deleteItem(@Path("itemId") Integer itemId,@Header("Authorization") String token);

    @GET("api/items/item/{itemId}")
    Call<Item> getItem(@Path("itemId") Integer itemId,@Header("Authorization") String token);
}
