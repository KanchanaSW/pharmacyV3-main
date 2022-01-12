package com.eea.pms.RetrofitInterface;

import com.eea.pms.Model.Inquiry;
import com.eea.pms.Model.Item;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.Model.Order;
import com.eea.pms.Model.OrderedItems;
import com.eea.pms.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdminApi {
    //get all users
    @GET("api/users/all")
    Call<List<User>> getAllUsers(@Header("Authorization") String token);
    //get single user details
    @GET("api/users/user")
    Call<User> get( @Header("Authorization") String token);
    //update status of the user pending --> verified
    //@GET("api/users/update-status/{userId}")
   // Call<Void> updateUserStatus(@Path("userId") Integer userId, @Header("Authorization") String token);
    //update user account details
    @POST("api/users/update-user")
    Call<String> updateMyAcc(@Body User user, @Header("Authorization") String token);

    //get all item requests
    @GET("api/requests/all")
    Call<List<ItemRequests>> requestsList(@Header("Authorization") String token);
    //get request by id
    @GET("api/requests/getMI/{itemRequestsId}")
    Call<ItemRequests> getMI(@Path("itemRequestsId") Integer itemRequestsId, @Header("Authorization") String token);
    //add manage Request
    @POST("api/requests/ManageRequestAdd")
    Call<Item> manageRequest(@Body Item item, @Header("Authorization") String token);
    //reject item request with a note
    @POST("api/requests/RejectItemRequest")
    Call<ItemRequests> rejectReq(@Body ItemRequests itemRequests, @Header("Authorization") String token);

    //view all inquires
    @GET("api/inquiry/ViewAllInquires")
    Call<List<Inquiry>> getAllInquires(@Header("Authorization") String token);

    //view all orders
    @GET("api/orders/allOrders")
    Call<List<Order>> getAllOrders(@Header("Authorization") String token);
    //view ordered items of a order
    @GET("api/orders/viewSingleOrder/{orderedItemDTOId}")
    Call<List<OrderedItems>> getOrderedItems(@Path("orderedItemDTOId") Integer orderedItemDTOId, @Header("Authorization") String token);
}
