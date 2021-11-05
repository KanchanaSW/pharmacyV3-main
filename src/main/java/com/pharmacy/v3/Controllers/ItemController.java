package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Response.MessageResponse;
import com.pharmacy.v3.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/items")
@RestController
public class ItemController {

    @Autowired
    private EntityManager entityManager;

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //add new item
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/new-item")
    public ResponseEntity<?> addItem(@RequestBody ItemDTO item, HttpServletRequest request) {
        return itemService.addItem(item);
    }

    //view all items
    @GetMapping(value = "/itemAll")
    public List<Item> getAllItems() {
       return itemService.getAllItems();
    }

    @GetMapping(value = "/item/{itemId}")
    public ResponseEntity<?> getProductsById(@PathVariable Integer itemId) {
        Item item=itemService.getItemById(itemId);
        return ResponseEntity.ok(item);
    }

    //delete item
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/deleteItem/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Integer itemId) {
         return itemService.deleteItemByItemId(itemId);
    }

    //update Item
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/updateItem/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable Integer itemId, @RequestBody ItemDTO updateItem) {
        return itemService.updateItemById(itemId, updateItem);
    }

  /*  @RequestMapping(value = ("/category/item-all/{category}"))
    public List<Item> getItemByCategoryId(@PathVariable String categoryName , HttpServletRequest request){
        return itemService.getAllItemsByCategory(categoryName);
    }*/

}
