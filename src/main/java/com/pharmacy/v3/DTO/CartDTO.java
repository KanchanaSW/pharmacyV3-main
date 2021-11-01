package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;

import javax.persistence.*;

public class CartDTO {
    private Integer cartId;
    private User user;
    private Item item;
    private int quantity;
    private double total;
    private boolean isPurchased;

    public CartDTO() {
    }
    public CartDTO(int quantity,double total){
        this.quantity = quantity;
        this.total = total;
    }

    public CartDTO(User user, Item item, int quantity, double total, boolean isPurchased) {
        this.user = user;
        this.item = item;
        this.quantity = quantity;
        this.total = total;
        this.isPurchased = isPurchased;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
