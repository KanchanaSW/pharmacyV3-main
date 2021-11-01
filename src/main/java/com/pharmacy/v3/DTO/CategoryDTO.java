package com.pharmacy.v3.DTO;

public class CategoryDTO {
    private Integer categoryId;
    private String category;

    public CategoryDTO(String category) {
        this.category = category;
    }

    public CategoryDTO() {
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
