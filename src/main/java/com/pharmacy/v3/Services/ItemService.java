package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.Category;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.ItemCategory;
import com.pharmacy.v3.Repositories.CategoryRepository;
import com.pharmacy.v3.Repositories.ItemCatgRepo;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private ItemCatgRepo itemCatgRepo;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository, ItemCatgRepo itemCatgRepo) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.itemCatgRepo = itemCatgRepo;
    }

    //add item
    public ResponseEntity<MessageResponse> addItem(Item newItem) {
        try {
            if (itemRepository.existsByItemName(newItem.getItemName())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Product with the name exists!!"));
            }
            Item item = new Item();
            item.setItemName(newItem.getItemName());
            item.setDes(newItem.getDes());
            item.setPrice(newItem.getPrice());
            item.setQuantity(newItem.getQuantity());
            String[] categoryList = newItem.getCategoryList();
            item.setCategoryList(categoryList);
            itemRepository.save(item);

            for (int i = 0; i < categoryList.length; i++) {
                Category category = categoryRepository.categoryIs(categoryList[i]);
                // Category category=new Category();
                ItemCategory itemCategory = new ItemCategory();
                itemCategory.setItem(item);
                itemCategory.setCategory(category);
                itemCatgRepo.save(itemCategory);
            }
            return ResponseEntity.ok().body(new MessageResponse("Success: Item added"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //delete an item
    public ResponseEntity<?> deleteItemByItemId(Integer itemId) {
        try {
            if (itemRepository.existsById(itemId)) {
                itemCatgRepo.deleteItemCategory(itemId);
                itemRepository.deleteById(itemId);
                return ResponseEntity.ok().body(new MessageResponse("Success: Item deleted."));
            } else {
                return ResponseEntity.ok().body(new MessageResponse("Error: item not available"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //view all the items
    public ResponseEntity<?> getAllItems() {
        try {
            return ResponseEntity.ok().body(itemRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //view item by id
    public ResponseEntity<?> getItemById(Integer id) {
        try {
            if (!itemRepository.existsById(id)) {
                return ResponseEntity.ok().body(new MessageResponse("Error: item not available"));
            } else {
                Item item = itemRepository.findById(id).get();
                return ResponseEntity.ok().body(item);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //update item details
    public ResponseEntity<?> updateItemById(Integer itemId, Item updateItem) {
        try {
            if (itemRepository.existsById(itemId)) {
                Item item = itemRepository.findById(itemId).get();
                item.setItemName(updateItem.getItemName());
                item.setDes(updateItem.getDes());
                item.setPrice(updateItem.getPrice());
                item.setQuantity(updateItem.getQuantity());
                itemRepository.save(item);

                return ResponseEntity.ok().body(item);
            } else {
                return ResponseEntity.ok().body("Error: Item not available with the name");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
/*
    public List<Item> getAllItemsByCategory(String itemCategoryName){
        List<Item> itemList=itemRepository.findByItemCategoryName(itemCategoryName);
        return itemList;
    }*/
}
