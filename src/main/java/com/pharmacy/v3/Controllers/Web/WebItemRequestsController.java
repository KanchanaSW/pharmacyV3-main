package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.DTO.ItemRequestsDTO;
import com.pharmacy.v3.Models.Category;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.ItemRequests;
import com.pharmacy.v3.Services.CategoryService;
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
    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;

    //redirecting to item request page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/AddRequestPage")
    public String addRequestPage(Model model){
        model.addAttribute("AddRequest","addRequest");
        return "AddRequest";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/AddRequest")
    public String addItemRequest(@ModelAttribute("AddRequest")ItemRequestsDTO itemRequestsDTO, HttpServletRequest request, Model model){
        try{
            ResponseEntity<?> itemRequest=itemRequestsService.addItemRequestsService(itemRequestsDTO,request);
            if (itemRequest.getStatusCodeValue()==200){
                model.addAttribute("success", "Successfully Added");
            }else {
                model.addAttribute("error","item request already found");
            }
        }catch(Exception e){
            model.addAttribute("error", "Failed add request");
        }
        return "redirect:/MyRequests";
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
        return "redirect:/AllRequests";
    }

    //display all the item requests of a user
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/MyRequests")
    public String viewMyRequests(HttpServletRequest request,Model model){
        try {
            List<ItemRequestsDTO> myList= itemRequestsService.getMyNewItemRequestsService(request);
            model.addAttribute("requests", myList);
        }catch (Exception e){
            model.addAttribute("error","empty");
        }
        return "ViewMyRequests";
    }
    //delete my request
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/DeleteMyRequest/{newItemRequestsId}")
    public String deleteMyItemRequest(@PathVariable(name = "newItemRequestsId")Integer newItemRequestsId,HttpServletRequest request ,Model model){
        try{
            itemRequestsService.deleteMyItemRequestedService(newItemRequestsId,request);
            model.addAttribute("success","ItemRequest Was Successfully Deleted");

        }catch(Exception e){
            model.addAttribute("error","Failed To Delete The Item");

        }
        return "redirect:/MyRequests";
    }

    //view All requests ADMIN function
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/AllRequests")
    public String allRequests(Model model){
        try {
            List<ItemRequestsDTO> allList= itemRequestsService.getAllNewItemRequestsService();
            if (! allList.isEmpty()) {
                model.addAttribute("requests", allList);
            }else {
                model.addAttribute("error","empty");
            }
        }catch (Exception e){
            model.addAttribute("error","error");
        }
        return "ViewAllRequests";
    }

    //redirecting to manage Request page.
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/ManageRequestPage/{newItemRequestsId}")
    public String manageRP(@PathVariable(name = "newItemRequestsId")Integer newItemRequestsId,Model model){

        ItemRequests ir= itemRequestsService.get(newItemRequestsId);
        List<Category> catList=categoryService.getAllCategories();
        model.addAttribute("cate",catList);
        model.addAttribute("ir",ir);
        model.addAttribute("ManageRequest",new ItemDTO());
        return "ManageRequest";
    }
    //manage request
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/ManageRequest")
    public String manageItemRequest(@ModelAttribute("ManageRequest")ItemDTO itemDTO,HttpServletRequest request ,Model model){
        try{
             System.out.println("////////"+itemDTO.getItemId());
            ResponseEntity<?> newItem=itemService.addItem(itemDTO);
            if (newItem.getStatusCodeValue()==406){
                model.addAttribute("error","item already found");
            }else {
                itemRequestsService.deleteMyItemRequestedService(itemDTO.getItemId(), request);
                model.addAttribute("success", "Successfully Added");
            }
        }catch(Exception e){
            model.addAttribute("error", "Failed add");
        }
        return "redirect:/AllRequests";
    }

}

















