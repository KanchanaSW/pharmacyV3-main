package com.pharmacy.v3.IntegrationTests;

import com.pharmacy.v3.V3Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = V3Application.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllUsers() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/all")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

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
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/update-user")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void updateStatus() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/update-status/123")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void registerUser() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void loginUser() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }
}
