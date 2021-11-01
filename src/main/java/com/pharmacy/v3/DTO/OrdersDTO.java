package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.User;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OrdersDTO {
    private Integer ordersId;

    private String date;
    private String status;
    private String city;
    private String address;
    private double total;

    private User user;

    public OrdersDTO() {
    }

    public OrdersDTO(String date, String status, String city, String address, double total, User user) {
        this.date = date;
        this.status = status;
        this.city = city;
        this.address = address;
        this.total = total;
        this.user = user;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
