package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.Category;
import com.pharmacy.v3.Models.Item;

public class ItemCategoryDTO {
    private Integer itemCategoryId;

    private Item item;
    private Category category;

    public ItemCategoryDTO(){}

    public ItemCategoryDTO(Item item, Category category) {
        this.item = item;
        this.category = category;
    }

    public Integer getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
