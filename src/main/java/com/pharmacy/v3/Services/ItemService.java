package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.CategoryDTO;
import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.Models.Category;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Repositories.CategoryRepository;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;


    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;

    }

    //add item
    public ResponseEntity<?> addItem(ItemDTO newItem) {
        try {
            if (itemRepository.existsByItemName(newItem.getItemName())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("exists"));
            }
            Item item = new Item();
            item.setItemName(newItem.getItemName());
            item.setDes(newItem.getDes());
            item.setPrice(newItem.getPrice());
            item.setQuantity(newItem.getQuantity());
            Category category=categoryService.getCategory(newItem.getCategoryName());
            item.setCategory(category);
            itemRepository.save(item);

            return ResponseEntity.ok().body(new MessageResponse("Success: Item added"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //delete an item
    public ResponseEntity<?> deleteItemByItemId(Integer itemId) {
        try {
            if (itemRepository.existsById(itemId)) {
                itemRepository.deleteById(itemId);
                return ResponseEntity.ok().body(new MessageResponse("Success: Item deleted."));
            } else {
                return ResponseEntity.unprocessableEntity().body(new MessageResponse("Error: item not available"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //view all the items
    public List<ItemDTO> getAllItems() {
        List<ItemDTO> list=new ArrayList<>();
        for (Item item : itemRepository.findAll()){
            ItemDTO i=new ItemDTO();
            i.setItemId(item.getItemId());
            i.setItemName(item.getItemName());
            i.setDes(item.getDes());
            i.setPrice(item.getPrice());
            i.setQuantity(item.getQuantity());
            i.setCategoryName(item.getCategory().getCategory());
            list.add(i);
        }
        return list;
    }

    /*
    //view categories in addd item page
    public List<CategoryDTO> getAllCate(){
        List<CategoryDTO> list=new ArrayList<>();
        for (Category category : categoryRepository.findAll()){
            CategoryDTO categoryDTO=new CategoryDTO();
            categoryDTO.setCategory(category.getCategory());
            categoryDTO.setCategoryId(category.getCategoryId());
            list.add(categoryDTO);
        }
        return list;
    }
    */
    //view item by id
    public ItemDTO getItemById(Integer id) {
        Optional<Item> item = itemRepository.findById(id);
        ItemDTO info=new ItemDTO();
        Item u=null;
        if(item.isPresent()){
            u = item.get();
            info.setItemId(u.getItemId());
            info.setItemName(u.getItemName());
            info.setDes(u.getDes());
            info.setQuantity(u.getQuantity());
            info.setPrice(u.getPrice());
            info.setCategoryName(u.getCategory().getCategory());
        }
        return info;
    }
    public void updateQuantity(int itemId,int quantity){
        Item item = itemRepository.findById(itemId).get();
        item.setQuantity(quantity);
        itemRepository.save(item);
    }

    //update item details
    public ResponseEntity<?> updateItemById(Integer itemId, ItemDTO updateItem) {
        try {
            if (itemRepository.existsById(itemId)) {
                Item item = itemRepository.findById(itemId).get();
                item.setItemName(updateItem.getItemName());
                item.setDes(updateItem.getDes());
                item.setPrice(updateItem.getPrice());
                //updateQuantity(itemId,updateItem.getQuantity());
                item.setQuantity(updateItem.getQuantity());
                Category category=categoryService.getCategory(updateItem.getCategoryName());
                item.setCategory(category);
                itemRepository.save(item);

                return ResponseEntity.ok().body(item);
            } else {
                return ResponseEntity.unprocessableEntity().body("Error: Item not available with the name");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
    public Item find(int itemId){return itemRepository.findById(itemId).get();}
    public Item save(Item item){return itemRepository.save(item);}
/*
    public List<Item> getAllItemsByCategory(String itemCategoryName){
        List<Item> itemList=itemRepository.findByItemCategoryName(itemCategoryName);
        return itemList;
    }*/
}
