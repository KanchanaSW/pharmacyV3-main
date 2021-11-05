package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
public class WebItemController {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ItemService itemService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/NewItem")
    public String addNewItem(@ModelAttribute("newItem")ItemDTO itemDTO, Model model){
         try{
            ResponseEntity<?> newItem=itemService.addItem(itemDTO);
            if (newItem.getStatusCodeValue()==406){
                model.addAttribute("error","item already found");
            }else {
                model.addAttribute("success", "Successfully Added");
            }
        }catch(Exception e){
            model.addAttribute("error", "Failed add");
        }
         return "AddItem";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/ViewAllItems")
    public String viewAllItems(Model model){
        try {
            List<Item> allItems = itemService.getAllItems();
            model.addAttribute("newItem", allItems);
        }catch (Exception e){
            model.addAttribute("error","empty");
        }
        return "ViewAllItems";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/ViewItem/{itemId}")
    public String viewAnItem(@PathVariable(name = "itemId")Integer itemId, Model model){
        try{
            Item item= itemService.getItemById(itemId);
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
    @PutMapping(value = "/UpdateItemPage/{itemId}")
    public String updateItemPage(@PathVariable(name = "itemId")Integer itemId,Model model){
        Item item= itemService.getItemById(itemId);
        model.addAttribute("itemInfo",item);
        model.addAttribute("updateInfo",new ItemDTO());
        return "UpdateItem";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/UpdateItem")
    public String updateItem(@ModelAttribute("updateInfo")ItemDTO itemDTO,Model model){
        try{
            itemService.updateItem(itemDTO);
            model.addAttribute("success", "Successfully Updated The Item");
        }catch(Exception e){
            model.addAttribute("error", "Failed To Update The Item");
        }
        return "/UpdateItem";
    }
}













