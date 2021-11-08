package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.CategoryDTO;
import com.pharmacy.v3.Models.Category;

import com.pharmacy.v3.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/category")
@RestController
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/createCategory")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO newCategory) {
        return categoryService.addNewCategory(newCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/admin/deleteCategory{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        return categoryService.deleteCategory(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCategories() {
        List<Category> list=categoryService.getAllCategories();
        if (list.isEmpty()) {
            return ResponseEntity.ok().body("empty");
        }else {
            return ResponseEntity.ok().body(list);
        }
    }

/*
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/item-category/all")
    public ResponseEntity<?> getAllItemCatogries() {
        List<ItemCategory> icList=categoryService.getAllItemCategories();
        if (icList.isEmpty()){
            return ResponseEntity.ok().body("empty");
        }else {
            return ResponseEntity.ok().body(icList);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/item-category/{categoryId}")
    public ResponseEntity<?> getAllItemCategoryById(@PathVariable Integer categoryId) {
        List<ItemCategory> list=categoryService.getAllItemByCategoryId(categoryId);
        if (list.isEmpty()) {
            return ResponseEntity.ok().body("empty");
        }else {
            return ResponseEntity.ok().body(list);
        }
    }*/
}
