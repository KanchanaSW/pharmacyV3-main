package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebOrdersController {
    @Autowired
    private OrdersService ordersService;

    //redirect to place order page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/placeNewOrderPage")
    public String viewAnItem(@PathVariable(name = "itemId")Integer itemId, Model model){
        try{

        }catch (Exception e){
            model.addAttribute("error" ,"empty");
        }
        return "PlaceNewOrder";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/PlaceNewOrder")
    public String addNewOrder(@ModelAttribute("newOrder")Orders orders, HttpServletRequest request, Model model){
        ResponseEntity<?> nOrder=ordersService.addOrder(request,orders);
        if (nOrder.getStatusCodeValue()==200){
            model.addAttribute("success","Order added success");
        }else{
            model.addAttribute("error","cannont add order");
        }
        return "ViewAllMyOrders";
    }
}
