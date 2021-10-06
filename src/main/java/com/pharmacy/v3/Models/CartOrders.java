package com.pharmacy.v3.Models;

import javax.persistence.*;

@Entity
public class CartOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartOrdersId;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart", referencedColumnName = "cartId")
    private Cart cart;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "orders", referencedColumnName = "ordersId")
    private Orders orders;

    public CartOrders() {
    }

    public CartOrders(Cart cart, Orders orders) {
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
