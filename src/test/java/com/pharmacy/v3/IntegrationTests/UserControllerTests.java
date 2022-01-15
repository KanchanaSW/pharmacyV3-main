package com.pharmacy.v3.IntegrationTests;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacy.v3.Controllers.AuthController;
import com.pharmacy.v3.Controllers.UserController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pharmacy.v3.Models.Role;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.RoleRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Services.UserService;
import com.pharmacy.v3.V3Application;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import static org.mockito.BDDMockito.given;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(controllers = UserController.class)
@ActiveProfiles
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserController userController;
    @MockBean
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(26,"testingUser12","abcgdfe123@gmail.com","0771556181","password","pending",null));
        this.userList.add(new User(27,"testingUser123","abcgdfe1233@gmail.com","0771556181","password","pending",null));
        this.userList.add(new User(29,"testingUser122","abcgdfe1232@gmail.com","0771556181","password","pending",null));

    }

    @Test
    public void getAllUsers() throws Exception {

        given(userService.getAllUsers()).willReturn(userList);

        this.mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userList.size())));
    }





}
/*    @Test
    public void getAllUsers() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/all")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }*/
/*
    @Test
    public void getAUsers() throws Exception {
       MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/user")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
   public void updateAUsers() throws Exception {
        String json ="{\n" +
                "  \"userId\": 111,\n" +
                "  \"phone\": \"0771556815\",\n" +
                "  \"password\": \"password\"\n" +
                "}";
        mockMvc.perform(
                post("/api/users/update-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());

     /*   MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/update-user")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());*/


 /*   @Test
    public void updateStatus() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/update-status/123")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }*/

/*    @Test
    public void registerUser() throws Exception {
        //language=JSON
        String json ="{\n" +
                "  \"username\": \"test\",\n" +
                "  \"email\": \"sk.persame@gmail.com\",\n" +
                "  \"phone\": \"0771556815\",\n" +
                "  \"password\": \"password\"\n" +
                "}";
        MvcResult mvcResult= mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk()).andReturn();
                System.out.println(mvcResult);
                //.andExpect(jsonPath("$.username", Matchers.is("test")))
                //.andExpect(jsonPath("$.phone", Matchers.is("0771556815")));

        *//*MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());*//*
    }*/

/*    @Test
    public void loginUser() throws Exception {
        //language=JSON
        String json ="{\n" +
                "  \"username\": \"test\",\n" +
                "  \"password\": \"password\"\n" +
                "}";
         mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());
         MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }*/

