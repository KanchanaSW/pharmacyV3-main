package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.CategoryDTO;
import com.pharmacy.v3.Models.Category;
import com.pharmacy.v3.Models.ItemCategory;
import com.pharmacy.v3.Repositories.CategoryRepository;
import com.pharmacy.v3.Repositories.ItemCatgRepo;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ItemCatgRepo itemCatgRepo;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ItemCatgRepo itemCatgRepo) {
        this.categoryRepository = categoryRepository;
        this.itemCatgRepo = itemCatgRepo;
    }

    //add new category
    public ResponseEntity<?> addNewCategory(CategoryDTO newCategory) {
        try {
            if (categoryRepository.existsByCategory(newCategory.getCategory())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: category already exists."));
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
                if (!itemCatgRepo.existsByItemCategoryId(id)) {
                    categoryRepository.deleteById(id);
                    return ResponseEntity.ok().body(new MessageResponse("Success: Category deleted success."));
                } else {
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to delete category "));
                }
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No Category found with the name"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //public view all categories
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categoryList = categoryRepository.findAllByOrderByCategoryIdDesc();
            return ResponseEntity.ok().body(categoryList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<?> getAllItemCategories() {
        try {
            Iterable<ItemCategory> list = itemCatgRepo.findAll();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<?> getAllItemByCategoryId(Integer id) {
        try {
            List<ItemCategory> list = itemCatgRepo.findByItemCategoryId(id);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
}
