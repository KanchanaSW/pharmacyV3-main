package com.pharmacy.v3.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pharmacy.v3.Models.Category;

import javax.persistence.Transient;

public class ItemDTO {
    private Integer itemId;

    private String itemName;
    private String des;
    private double price;
    private int quantity;
    private String categoryName;

    private Category category;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String[] categoryList;

    public ItemDTO(){}

    public ItemDTO(String itemName,String des,int quantity,Category category,double price){
        this.itemName = itemName;
        this.des = des;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
    }

    public ItemDTO(String itemName, String des, double price, int quantity, Category category) {
        this.itemName = itemName;
        this.des = des;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String[] getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(String[] categoryList) {
        this.categoryList = categoryList;
    }
}
