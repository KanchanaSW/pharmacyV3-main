package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.Models.Category;
import com.pharmacy.v3.Models.ItemCategory;
import com.pharmacy.v3.Response.MessageResponse;
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
    public ResponseEntity<MessageResponse> addCategory(@RequestBody Category newCategory){
        return categoryService.addNewCategory(newCategory);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/admin/deleteCategory")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/all")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/item-category/all")
    public Iterable<ItemCategory> getAllItemCatogries(){
        return categoryService.getAllItemCategories();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/item-category/{categoryId}")
    public List<ItemCategory> getAllItemCategoryById(@PathVariable Integer categoryId){
        return categoryService.getAllItemByCategoryId(categoryId);
    }
}
