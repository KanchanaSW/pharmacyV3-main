package com.eea.pms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.eea.pms.Model.Inquiry;
import com.eea.pms.Model.Item;
import com.eea.pms.Model.ItemRequests;
import com.eea.pms.Model.LoginRequest;
import com.eea.pms.Model.LoginResponse;
import com.eea.pms.Model.Order;
import com.eea.pms.Model.User;
import com.eea.pms.RetrofitClient.RetrofitClient;
import com.eea.pms.RetrofitInterface.AdminApi;
import com.eea.pms.RetrofitInterface.AuthenticationApi;
import com.eea.pms.RetrofitInterface.ItemApi;
import com.eea.pms.RetrofitInterface.UserApi;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ServicesTestSuit {
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "password";
    private final String USER_USERNAME = "user";
    private final String USER_PASSWORD = "password";

    @Test
    public void testLoginAdmin() throws IOException {
        LoginRequest loginRequest = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        loginResponse.body().getRoles().equals("ROLE_ADMIN");
        assertEquals("Login as Admin", "ROLE_ADMIN", loginResponse.body().getRoles());
        System.out.println("Login as Admin:\tPASSED");
    }

    @Test
    public void testLoginUSER() throws IOException {
        LoginRequest loginRequest = new LoginRequest(USER_USERNAME, USER_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        loginResponse.body().getRoles().equals("ROLE_USER");
        assertEquals("Login as User", "ROLE_USER", loginResponse.body().getRoles());
        System.out.println("Login as User:\tPASSED");
    }

    @Test
    public void testLoginUSERwithInvalidPass() throws IOException {
        LoginRequest loginRequest = new LoginRequest(USER_USERNAME, "123");
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        //  loginResponse.body().getRoles().equals("ROLE_USER");
        assertTrue("Login with Invalid Pass", loginResponse.code() == 400);
        System.out.println("Login with Invalid Pass:\tPASSED");
    }

    @Test
    public void getAllUserSTEst() throws IOException {
        LoginRequest loginRequest = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        assert loginResponse.body() != null;
        String jwtToken = "Bearer " + loginResponse.body().getToken();
        Call<List<User>> getAllUsers = RetrofitClient.getRetrofitClientInstance().create(AdminApi.class)
                .getAllUsers(jwtToken);
        List<User> list = getAllUsers.execute().body();
        //  Response<List<User>> response = getAllUsers.execute();
        // List<User> users = response.body();
        assert list != null;
        assertTrue("Get all users =>>", list.size() > 0);
        System.out.println("Get All Users:\tPASSED");
    }

    @Test
    public void getAllRequestTest() throws IOException {
        LoginRequest loginRequest = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        assert loginResponse.body() != null;
        String jwtToken = "Bearer " + loginResponse.body().getToken();

        Call<List<ItemRequests>> getAll = RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).requestsList(jwtToken);

        Response<List<ItemRequests>> response = getAll.execute();
        List<ItemRequests> ir = response.body();
        assertTrue("Get all ItemRequests =>>", response.isSuccessful());
        System.out.println("Get All ItemRequests:\tPASSED");
    }

    @Test
    public void getAllInquiresTest() throws IOException {
        LoginRequest loginRequest = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        assert loginResponse.body() != null;
        String jwtToken = "Bearer " + loginResponse.body().getToken();

        Call<List<Inquiry>> getInquires= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).getAllInquires(jwtToken);

        Response<List<Inquiry>> response = getInquires.execute();
        List<Inquiry> ir = response.body();
        assertTrue("Get all Inquiry =>>", response.isSuccessful());
        System.out.println("Get All Inquiry:\tPASSED");
    }

    @Test
    public void getAllOrdersTest() throws IOException {
        LoginRequest loginRequest = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        assert loginResponse.body() != null;
        String jwtToken = "Bearer " + loginResponse.body().getToken();

        Call<List<Order>> getAll= RetrofitClient.getRetrofitClientInstance().create(AdminApi.class).getAllOrders(jwtToken);

        Response<List<Order>> response = getAll.execute();
        List<Order> ir = response.body();
        assertTrue("Get all Order =>>", response.isSuccessful());
        System.out.println("Get All Order:\tPASSED");
    }

    @Test
    public void getAllItemsTest() throws IOException {
        LoginRequest loginRequest = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        assert loginResponse.body() != null;
        String jwtToken = "Bearer " + loginResponse.body().getToken();

        Call<List<Item>> getItemsList= RetrofitClient.getRetrofitClientInstance().create(ItemApi.class).getAllItems(loginResponse.body().getToken());

        Response<List<Item>> response = getItemsList.execute();
        List<Item> ir = response.body();
        assertTrue("Get all Item =>>", response.isSuccessful());
        System.out.println("Get All Item:\tPASSED"+"\t"+ir.size());
    }

    @Test
    public void getMyInquiresTest() throws IOException {
        LoginRequest loginRequest = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        assert loginResponse.body() != null;
        String jwtToken = "Bearer " + loginResponse.body().getToken();

        Call<List<Inquiry>> getInquires= RetrofitClient.getRetrofitClientInstance().create(UserApi.class).getMyInquires(jwtToken);

        Response<List<Inquiry>> response = getInquires.execute();
        List<Inquiry> ir = response.body();
        assertTrue("Get all Inquiry =>>", response.isSuccessful());
        System.out.println("Get All Inquiry:\tPASSED"+"\t"+ir.size());
    }

    @Test
    public void getMyRequestsTest() throws IOException {
        LoginRequest loginRequest = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        Call<LoginResponse> login = RetrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class)
                .loginUser(loginRequest);
        Response<LoginResponse> loginResponse = login.execute();
        assert loginResponse.body() != null;
        String jwtToken = "Bearer " + loginResponse.body().getToken();

        Call<List<ItemRequests>> getMyRequests= RetrofitClient.getRetrofitClientInstance().create(UserApi.class).getMyRequests(jwtToken);

        Response<List<ItemRequests>> response = getMyRequests.execute();
        List<ItemRequests> ir = response.body();
        assertTrue("Get all ItemRequests =>>", response.isSuccessful());
        System.out.println("Get All ItemRequests:\tPASSED"+"\t"+ir.size());
    }



}
