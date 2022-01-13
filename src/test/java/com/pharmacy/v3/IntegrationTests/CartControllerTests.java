package com.pharmacy.v3.IntegrationTests;

import com.pharmacy.v3.V3Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = V3Application.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void addToCart() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cart/add-cart/12")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void viewMyCartItems() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cart/cartAll")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void deleteCartItemFromCart() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cart/delete-item/12")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void updateCartItem() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cart/update-cart/12")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void getAllUnPurchasedCartItems() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cart/viewCartAll")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void deleteCartId() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cart/delete/12")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse());
    }

}














