package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.CategoryDTO;
import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.Category;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Services.CartService;
import com.pharmacy.v3.Services.CategoryService;
import com.pharmacy.v3.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;

    //redirecting to Add item page.
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/NewItemPage")
    public String newItemPage(Model model){
        List<Category> catList=categoryService.getAllCategories();

        model.addAttribute("cate",catList);
        model.addAttribute("AddItem",new ItemDTO());
        return "AddItem";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/AddItem")
    public String addNewItem(@ModelAttribute("AddItem")ItemDTO itemDTO, Model model){
         try{
            // System.out.println("////////"+itemDTO.getCategoryName());
            ResponseEntity<?> newItem=itemService.addItem(itemDTO);
            if (newItem.getStatusCodeValue()==406){
                model.addAttribute("error","item already found");
            }else {
                model.addAttribute("success", "Successfully Added");
            }
        }catch(Exception e){
            model.addAttribute("error", "Failed add");
        }
         return "/AddItem";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('CUSTOMER')")
    @GetMapping(value = "/ViewAllItems")
    public String viewAllItems(Model model){
        try {
            List<ItemDTO> allItems = itemService.getAllItems();
            model.addAttribute("info", allItems);
        }catch (Exception e){
            model.addAttribute("error","empty");
        }
        return "ViewAllItems";
    }
    //search item
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/SearchItem")
    public String seacrhItem(HttpServletRequest request,Model model){
        String name=request.getParameter("searchItem");
        List<ItemDTO> seachItems=itemService.seachItem(name);
        model.addAttribute("info",seachItems);
        return "ViewAllItems";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/ViewItem/{itemId}")
    public String viewAnItem(@PathVariable(name = "itemId")Integer itemId, Model model){
        try{
            ItemDTO item= itemService.getItemById(itemId);
            model.addAttribute("itemInfo",item);
        }catch (Exception e){
            model.addAttribute("error" ,"empty");
        }
        return "ViewItem";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/DeleteItem/{itemId}")
    public String deleteAnItem(@PathVariable(name = "itemId")Integer itemId,Model model){
        try{
            itemService.deleteItemByItemId(itemId);
            model.addAttribute("success","Item Was Successfully Deleted");

        }catch(Exception e){
            model.addAttribute("error","Failed To Delete The Item");

        }
        return "redirect:/ViewAllItems";
    }
    //redirecting to update page.
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/UpdateItemPage/{itemId}")
    public String updateItemPage(@PathVariable(name = "itemId")Integer itemId,Model model){
        ItemDTO item= itemService.getItemById(itemId);
        if (item==null){
            model.addAttribute("error","empty");
        }
        List<Category> catList=categoryService.getAllCategories();
        model.addAttribute("cate",catList);

        model.addAttribute("itemInfo",item);
        model.addAttribute("updateInfo",new ItemDTO());
        return "UpdateItem";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/UpdateItem")
    public String updateItem(@ModelAttribute("updateInfo")ItemDTO itemDTO,Model model){
        try{
           ResponseEntity<?>uI= itemService.updateItemById(itemDTO.getItemId(),itemDTO);
            if (uI.getStatusCodeValue()==200){
                model.addAttribute("success", "Successfully Updated The Item");
            }else {
                model.addAttribute("error", "Failed");
            }
        }catch(Exception e){
            model.addAttribute("error", "Failed");
        }
        return "UpdateItem";
    }



}













