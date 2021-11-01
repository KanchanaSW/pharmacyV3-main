package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.Orders;

import javax.persistence.*;

public class CartOrdersDTO {

    private Integer cartOrdersId;
    private Cart cart;
    private Orders orders;

    public CartOrdersDTO() {
    }

    public CartOrdersDTO(Cart cart, Orders orders) {
        this.cart = cart;
        this.orders = orders;
    }

    public Integer getCartOrdersId() {
        return cartOrdersId;
    }

    public void setCartOrdersId(Integer cartOrdersId) {
        this.cartOrdersId = cartOrdersId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
