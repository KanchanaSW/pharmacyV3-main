package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.DTO.OrderDTO;
import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Services.CartService;
import com.pharmacy.v3.Services.OrdersService;
import com.pharmacy.v3.Services.UserService;
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
import java.util.Arrays;
import java.util.List;

@Controller
public class WebOrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    //redirect to choose customer
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/chooseCustomerPage")
    public String chooseCustomer( Model model){
        try{
            List<User> cusList=userService.getCustomers();
            System.out.println(cusList.get(0).getUsername());
            model.addAttribute("cus",cusList);
        }catch (Exception e){
            model.addAttribute("error" ,"empty");
        }
        return "SelectCustomer";
    }

    //redirect to place order page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/placeNewOrderPage/{userId}")
    public String placeOrder(@PathVariable(name = "userId")int userId, Model model){
        try{
            List<Cart> cusromerCart=cartService.getUserCart(userId);
            model.addAttribute("cItem",cusromerCart);
        }catch (Exception e){
            model.addAttribute("error" ,"empty");
        }
        return "PlaceNewOrder";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/PlaceNewOrder")
    public String addNewOrder(@ModelAttribute("newOrder") OrderDTO orders, HttpServletRequest request, Model model){
        System.out.println(orders.getCusName());
       ResponseEntity<?> nOrder=ordersService.addOrder(request,orders);
        if (nOrder.getStatusCodeValue()==200){
            model.addAttribute("success","Order added success");
        }else{
            model.addAttribute("error","cannont add order");
        }
        return "redirect:/Error404";
    }
}
