package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.ItemRequestsDTO;
import com.pharmacy.v3.Models.ItemRequests;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Repositories.ItemRequestsRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Models.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    public ResponseEntity<?> addItemRequestsService(ItemRequestsDTO newRequestI, HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();

            if (itemRepository.existsByItemName(newRequestI.getNewItemName()) ) {
                return ResponseEntity.unprocessableEntity().body(new MessageResponse("exists"));
            } else {
                ItemRequests ir = new ItemRequests();
                ir.setNewItemName(newRequestI.getNewItemName());
                ir.setNote(newRequestI.getNote());
                ir.setUser(user);
                ir.setStatus("pending");
                itemRequestsRepository.save(ir);
                System.out.println(ResponseEntity.ok().body("Success: request added"));
                return ResponseEntity.ok().body(ir);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<?> deleteItemRequestedService(Integer newItemId) {
        try {
           // User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            if (itemRequestsRepository.existsById(newItemId)) {
                itemRequestsRepository.deleteById(newItemId);
                return ResponseEntity.ok().body(new MessageResponse("Success: deleted success"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: occured"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
    //delete my item request
    public ResponseEntity<?> deleteMyItemRequestedService(Integer newItemId,HttpServletRequest request) {
        try {
             User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            if (itemRequestsRepository.existsById(newItemId) && itemRequestsRepository.existsByUser(user)) {
                itemRequestsRepository.deleteById(newItemId);
                return ResponseEntity.ok().body(new MessageResponse("Success: deleted success"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: occured"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //view my requests
    public List<ItemRequestsDTO> getMyNewItemRequestsService(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        List<ItemRequestsDTO> list = new ArrayList<>();
        for (ItemRequests itemRequests : itemRequestsRepository.findByUserOrderByItemRequestsIdDesc(user)){
            ItemRequestsDTO i=new ItemRequestsDTO();
            i.setItemRequestsId(itemRequests.getItemRequestsId());
            i.setNewItemName(itemRequests.getNewItemName());
            i.setNote(itemRequests.getNote());
            i.setStatus(itemRequests.getStatus());
            list.add(i);
        }
        return list;
    }

    //Admin function
    public List<ItemRequestsDTO> getAllNewItemRequestsService() {
        List<ItemRequestsDTO> list = new ArrayList<>();
        for (ItemRequests itemRequests : itemRequestsRepository.findAll()){
            ItemRequestsDTO i=new ItemRequestsDTO();
            i.setItemRequestsId(itemRequests.getItemRequestsId());
            i.setNewItemName(itemRequests.getNewItemName());
            i.setNote(itemRequests.getNote());
            i.setUserId(itemRequests.getUser().getUserId());
            i.setUsername(itemRequests.getUser().getUsername());
            i.setStatus(itemRequests.getStatus());
            list.add(i);
        }

        return list;

    }
    public ItemRequests get(int itemId){
        return itemRequestsRepository.findById(itemId).get();
    }
    //manage request
    public ResponseEntity<?> manageRequest(int newItemId){
        ItemRequests ir=itemRequestsRepository.findById(newItemId).get();
        ir.setStatus("Completed");
        ir=itemRequestsRepository.save(ir);
        return ResponseEntity.ok(ir);
    }

    public ResponseEntity<?> rejectRequestService(ItemRequests requestI) {
        try {
          ItemRequests itemRequests=get(requestI.getItemRequestsId());
          itemRequests.setNote(requestI.getNote());
          itemRequests.setStatus("Rejected");
          itemRequests=itemRequestsRepository.save(itemRequests);
          return ResponseEntity.ok(itemRequests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
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
    //reject request
    public ResponseEntity<?> rejectRequest(int newItemId){
        ItemRequests ir=itemRequestsRepository.findById(newItemId).get();
        ir.setStatus("Rejected");
        ir=itemRequestsRepository.save(ir);
        return ResponseEntity.ok(ir);
    }

    private ItemRequests mapRequests(ItemRequests ir) {
        return new ItemRequests(ir.getItemRequestsId(), ir.getNewItemName(), ir.getNote(),ir.getStatus());
    }


}
