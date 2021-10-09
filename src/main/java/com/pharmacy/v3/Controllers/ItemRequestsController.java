package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.Models.ItemRequests;
import com.pharmacy.v3.Services.ItemRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/requests")
@RestController
public class ItemRequestsController {
    private ItemRequestsService itemRequestsService;

    @Autowired
    public ItemRequestsController(ItemRequestsService itemRequestsService) {
        this.itemRequestsService = itemRequestsService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/add-request")
    public ResponseEntity<?> addItemRequests(@RequestBody ItemRequests itemRequests, HttpServletRequest request) {
        return itemRequestsService.addItemRequestsService(itemRequests, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(value = "/delete-request/{newItemRequestsId}")
    public ResponseEntity<?> deleteNewItemRequests(@PathVariable Integer newItemRequestsId, HttpServletRequest request) {
        return itemRequestsService.deleteItemRequestedService(newItemRequestsId, request);
    }

    //display all the item requests of a user
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/my-requests")
    public List<ItemRequests> viewMyNewItemRequests(HttpServletRequest request) {
        return itemRequestsService.getMyNewItemRequestsService(request);
    }

    //view All requests ADMIN function
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<?> viewAllNewItemRequests(){
        return itemRequestsService.getAllNewItemRequestsService();
    }

}