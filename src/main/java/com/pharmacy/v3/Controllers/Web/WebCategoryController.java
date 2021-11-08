package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.CategoryDTO;
import com.pharmacy.v3.Models.Category;

import com.pharmacy.v3.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebCategoryController {
    @Autowired
    private CategoryService categoryService;

    //redirecting to create category
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/CreateCategoryPage")
    public String createCatPage(Model model){
        model.addAttribute("createCat","createCat");
        return "CreateCategory";
    }
    //create new category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/CreateCategory")
    public String newCategory(@ModelAttribute(name = "newCategory")CategoryDTO newCategory, Model model){
        ResponseEntity<?> cat=categoryService.addNewCategory(newCategory);
        if (cat.getStatusCodeValue()==200){
            model.addAttribute("success","category added");
        }else if (cat.getStatusCodeValue()==422){
            model.addAttribute("error","category exists");
        }else{
            model.addAttribute("error","error");
        }
        return "CategoryList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/DeleteCategory{categoryId}")
    public String deleteCat(@PathVariable(name = "categoryId")Integer categoryId,Model model){
        ResponseEntity<?> dc=categoryService.deleteCategory(categoryId);
        if (dc.getStatusCodeValue()==200){
            model.addAttribute("success","category deleted");
        }else if (dc.getStatusCodeValue()==422){
            model.addAttribute("error","unable to delete ");
        }else{
            model.addAttribute("error","error deleting category");
        }
        return "CategoryList";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/AllCategories")
    public String viewAllCategories(Model model) {
        List<Category> list=categoryService.getAllCategories();
        if (list.isEmpty()) {
           model.addAttribute("error","empty");
        }else {
            model.addAttribute("success",list);
        }
        return "CategoryList";
    }
    /*
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/AllItemCategory")
    public String viewAllItemCatogries(Model model) {
        List<ItemCategory> icList=categoryService.getAllItemCategories();
        if (icList.isEmpty()){
            model.addAttribute("error","empty");
        }else {
            model.addAttribute("success",icList);
        }
        return "ItemCategoryList";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/ItemCategory/{categoryId}")
    public String viewAllItemCategoryById(@PathVariable Integer categoryId,Model model) {
        List<ItemCategory> list=categoryService.getAllItemByCategoryId(categoryId);
        if (list.isEmpty()) {
            model.addAttribute("error","empty");
        }else {
            model.addAttribute("success",list);
        }
        return "ItemCategoryList";
    }*/

}














