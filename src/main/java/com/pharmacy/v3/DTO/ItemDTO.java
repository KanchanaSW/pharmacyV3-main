package com.pharmacy.v3.DTO;

import javax.persistence.Transient;

public class ItemDTO {
    private Integer itemId;

    private String itemName;
    private String des;
    private double price;
    private int quantity;

    @Transient
    String[] categoryList;

    public ItemDTO(){}

    public ItemDTO(String itemName, String des, double price, int quantity, String[] categoryList) {
        this.itemName = itemName;
        this.des = des;
        this.price = price;
        this.quantity = quantity;
        this.categoryList = categoryList;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String[] getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(String[] categoryList) {
        this.categoryList = categoryList;
    }
}
