package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.ItemRequestsDTO;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.ItemRequests;
import com.pharmacy.v3.Services.ItemRequestsService;
import com.pharmacy.v3.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebItemRequestsController {
    @Autowired
    private ItemRequestsService itemRequestsService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/AddRequest")
    public String addItemRequest(@ModelAttribute("newRequest")ItemRequestsDTO itemRequestsDTO, HttpServletRequest request, Model model){
        try{
            ResponseEntity<?> itemRequest=itemRequestsService.addItemRequestsService(itemRequestsDTO,request);
            if (itemRequest.getStatusCodeValue()==406){
                model.addAttribute("error","item request already found");
            }else {
                model.addAttribute("success", "Successfully Added");
            }
        }catch(Exception e){
            model.addAttribute("error", "Failed add request");
        }
        return "AddRequest";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/DeleteRequest/{newItemRequestsId}")
    public String deleteItemRequest(@PathVariable(name = "newItemRequestsId")Integer newItemRequestsId,Model model){
        try{
            itemRequestsService.deleteItemRequestedService(newItemRequestsId);
            model.addAttribute("success","ItemRequest Was Successfully Deleted");

        }catch(Exception e){
            model.addAttribute("error","Failed To Delete The Item");

        }
        return "redirect:/ViewAllItemsRequests";
    }

    //display all the item requests of a user
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/MyRequests")
    public String viewMyRequests(HttpServletRequest request,Model model){
        try {
            List<ItemRequests> myList= itemRequestsService.getMyNewItemRequestsService(request);
            model.addAttribute("requests", myList);
        }catch (Exception e){
            model.addAttribute("error","empty");
        }
        return "ViewMyRequests";
    }
    //view All requests ADMIN function
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/AllRequests")
    public String allRequests(Model model){
        try {
            List<ItemRequests> allList= itemRequestsService.getAllNewItemRequestsService();
            model.addAttribute("requests", allList);
        }catch (Exception e){
            model.addAttribute("error","empty");
        }
        return "ViewMyRequests";
    }

}

















