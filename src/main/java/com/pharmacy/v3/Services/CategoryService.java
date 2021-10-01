package com.pharmacy.v3.Services;

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
    public ResponseEntity<MessageResponse> addNewCategory(Category newCategory){
        if (categoryRepository.existsByCategory(newCategory.getCategory())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: category already exists."));
        }
        Category category=new Category(newCategory.getCategory());
        categoryRepository.save(category);
        return ResponseEntity.ok().body(new MessageResponse("Success: Category added success."));
    }

    //delete a category
    public ResponseEntity<?> deleteCategory(Integer id){
        if (categoryRepository.existsById(id)){
            if (!itemCatgRepo.existsByItemCategoryId(id)){
                categoryRepository.deleteById(id);
                return ResponseEntity.ok().body(new MessageResponse("Success: Category deleted success."));
            }else{
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to delete category "));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: No Category found with the name"));
    }

    //public view all categories
    public List<Category> getAllCategories(){
        List<Category> categoryList=categoryRepository.findAllByOrderByCategoryIdDesc();
        return categoryList;
    }

    public Iterable<ItemCategory> getAllItemCategories(){
        Iterable<ItemCategory> list=itemCatgRepo.findAll();
        return list;
    }

    public List<ItemCategory> getAllItemByCategoryId(Integer id){
        List<ItemCategory> list=itemCatgRepo.findByItemCategoryId(id);
        return list;
    }
}
