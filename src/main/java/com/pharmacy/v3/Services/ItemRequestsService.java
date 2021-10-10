package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.ItemRequests;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Repositories.ItemRequestsRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemRequestsService {
    private ItemRequestsRepository itemRequestsRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Autowired
    public ItemRequestsService(ItemRequestsRepository itemRequestsRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRequestsRepository = itemRequestsRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addItemRequestsService(ItemRequests newRequestI, HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();

            if (itemRepository.existsByItemName(newRequestI.getNewItemName()) && itemRequestsRepository.existsByUser(user)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error Item already exists check store"));
            } else {
                ItemRequests ir = new ItemRequests();
                ir.setNewItemName(newRequestI.getNewItemName());
                ir.setNote(newRequestI.getNote());
                ir.setUser(user);
                itemRequestsRepository.save(ir);
                System.out.println(ResponseEntity.ok().body("Success: request added"));
                return ResponseEntity.ok().body(new MessageResponse("" +
                        "Item Name: " + ir.getNewItemName() + " " +
                        "Item Note: " + ir.getNote() + " " +
                        "User Id: " + user.getUsername()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<?> deleteItemRequestedService(Integer newItemId, HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            if (itemRequestsRepository.existsByUser(user) && itemRequestsRepository.existsById(newItemId)) {
                itemRequestsRepository.deleteById(newItemId);
                return ResponseEntity.ok().body(new MessageResponse("Success: deleted success"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: occured"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<?> getMyNewItemRequestsService(HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();

            List<ItemRequests> list = itemRequestsRepository.findByUserOrderByItemRequestsIdDesc(user).
                    stream().map(this::mapRequests).collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //Admin function
    public ResponseEntity<?> getAllNewItemRequestsService() {
        try {
            List<ItemRequests> list = itemRequestsRepository.findAll();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    private ItemRequests mapRequests(ItemRequests ir) {
        return new ItemRequests(ir.getItemRequestsId(), ir.getNewItemName(), ir.getNote());
    }
    /*
     //Admin function
     public ResponseEntity<?> getAllNewItemRequestsService(){

     List<ItemRequests> list=itemRequestsRepository.findAll();
     List<String> newList=new ArrayList<String>();
     for (ItemRequests i:list){
     newList.add("Requested User : "+i.getUser().getUsername()+" Item Request Id : "+i.getItemRequestsId()+
     " Requested Item Name : "+i.getNewItemName()+" Note : "+i.getNote());
     }
     return ResponseEntity.ok().body(newList);
     }

     */


}
