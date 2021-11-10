package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.ItemRequestsDTO;
import com.pharmacy.v3.Models.ItemRequests;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Repositories.ItemRequestsRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            list.add(i);
        }

        return list;

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
