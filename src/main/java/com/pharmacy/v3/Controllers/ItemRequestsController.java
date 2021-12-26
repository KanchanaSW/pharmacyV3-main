package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.DTO.ItemRequestsDTO;
import com.pharmacy.v3.Models.Category;
import com.pharmacy.v3.Models.ItemRequests;
import com.pharmacy.v3.Services.ItemRequestsService;
import com.pharmacy.v3.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/requests")
@RestController
public class ItemRequestsController {
    private ItemRequestsService itemRequestsService;
    @Autowired
    private ItemService itemService;

    @Autowired
    public ItemRequestsController(ItemRequestsService itemRequestsService) {
        this.itemRequestsService = itemRequestsService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/add-request")
    public ResponseEntity<?> addItemRequests(@RequestBody ItemRequestsDTO itemRequests, HttpServletRequest request) {
        return itemRequestsService.addItemRequestsService(itemRequests, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(value = "/delete-request/{newItemRequestsId}")
    public ResponseEntity<?> deleteNewItemRequests(@PathVariable Integer newItemRequestsId) {
        return itemRequestsService.deleteItemRequestedService(newItemRequestsId);
    }

    //display all the item requests of a user
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/my-requests")
    public List<ItemRequestsDTO> viewMyNewItemRequests(HttpServletRequest request) {
        return itemRequestsService.getMyNewItemRequestsService(request);
    }

    //view All requests ADMIN function
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all")
    public List<ItemRequestsDTO> viewAllNewItemRequests() {
        return itemRequestsService.getAllNewItemRequestsService();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/getMI/{newItemRequestsId}")
    public ResponseEntity<?> manageRP(@PathVariable(name = "newItemRequestsId") Integer newItemRequestsId, Model model) {
        ItemRequests ir = itemRequestsService.get(newItemRequestsId);
        ItemRequestsDTO io = new ItemRequestsDTO();
        if (ir != null) {
            io.setNewItemName(ir.getNewItemName());
            io.setNote(ir.getNote());
        }
        return ResponseEntity.ok(io);
    }

    //manage request
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/ManageRequestAdd")
    public ResponseEntity<?> manageItemRequest(@RequestBody ItemDTO itemDTO) {
        ResponseEntity<?> newItem = itemService.addItem(itemDTO);
        System.out.println(itemDTO.getItemId());
        itemRequestsService.manageRequest(itemDTO.getItemId());
        //itemRequestsService.deleteMyItemRequestedService(itemDTO.getItemId(), request);
        return ResponseEntity.ok(newItem);
    }

    //reject request
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/RejectItemRequest")
    public ResponseEntity<?> rejectRequest(@RequestBody ItemRequests itemRequests){
        return itemRequestsService.rejectRequestService(itemRequests);
    }
}
