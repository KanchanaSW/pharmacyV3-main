package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.CategoryDTO;
import com.pharmacy.v3.Models.Category;

import com.pharmacy.v3.Repositories.CategoryRepository;

import com.pharmacy.v3.Models.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //add new category
    public ResponseEntity<?> addNewCategory(CategoryDTO newCategory) {
        try {
            if (categoryRepository.existsByCategory(newCategory.getCategory())) {
                return ResponseEntity.unprocessableEntity().body(new MessageResponse("Error: category already exists."));
            }
            Category category = new Category(newCategory.getCategory());
            categoryRepository.save(category);
            return ResponseEntity.ok().body(new MessageResponse("Success: Category added success."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //delete a category
    public ResponseEntity<?> deleteCategory(Integer id) {
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
                return ResponseEntity.ok().body(new MessageResponse("Success: Category deleted success."));
            }
            return ResponseEntity.unprocessableEntity().body(new MessageResponse("Error: No Category found with the name"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //public view all categories
    public List<Category> getAllCategories() {
        try {
            List<Category> categoryList = categoryRepository.findAllByOrderByCategoryIdDesc();
            return categoryList;
        } catch (Exception e) {
            return null;
        }
    }


    public Category getCategory(String categoryName){
        return categoryRepository.categoryIs(categoryName);

        /*
        try{
            if (categoryRepository.existsByCategory(categoryName)){
                return categoryRepository.categoryIs(categoryName);
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }*/
    }

/*
    public List<ItemCategory> getAllItemCategories() {
        try {
            List<ItemCategory> list = itemCatgRepo.findAll();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<ItemCategory> getAllItemByCategoryId(Integer id) {
        try {
            List<ItemCategory> list = itemCatgRepo.findByItemCategoryId(id);
            return list;
        } catch (Exception e) {
            return null;
        }
    }*/
}
