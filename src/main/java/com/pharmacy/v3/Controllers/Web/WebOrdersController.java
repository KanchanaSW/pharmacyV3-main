package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.CartOrdersDTO;
import com.pharmacy.v3.DTO.OrdersDTO;
import com.pharmacy.v3.Models.CartOrders;
import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebOrdersController {
    @Autowired
    private OrdersService ordersService;

    //redirect to add cart order page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/CartOrderPage")
    public String cartOrderPage(Model model){
        model.addAttribute("info","addInfo");
        return "AddCartOrder";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/AddCartOrder")
    public String createNewCartOrder(@ModelAttribute("orders")CartOrdersDTO cartOrdersDTO, HttpServletRequest request, Model model){
        ResponseEntity<?> co=ordersService.addCartOrders(cartOrdersDTO,request);
        if (co.getStatusCodeValue()==200){
            model.addAttribute("success","cart order added");
        }else{
            model.addAttribute("error","unable to add cart order");
        }
        return "ViewCartOrders";
    }

    //redirect to add order page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/AddOrderPage")
    public String addOrderPage(Model model){
        model.addAttribute("info","addInfo");
        return "AddOrder";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/AddOrder")
    public String addNewOrder(@ModelAttribute("orders") OrdersDTO ordersDTO, HttpServletRequest request, Model model){
        ResponseEntity<?> o=ordersService.addOrder(ordersDTO,request);
        if (o.getStatusCodeValue()==200){
            model.addAttribute("success","order added");
        }else{
            model.addAttribute("error","unable to add order");
        }
        return "ViewOrders";
    }
    //get all users orders
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/MyOrders")
    public String myOrders(HttpServletRequest request,Model model){
        List<Orders> orders=ordersService.getAllUserOrders("pending",request);
        if (orders.isEmpty()){
            model.addAttribute("error","empty");
        }else{
            model.addAttribute("info",orders);
        }
        return "ViewMyOrders";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/ViewCartOrders/{ordersId}")
    public String viewAllCartOrderId(@PathVariable(name = "orderId")Integer orderId,Model model){
        List<CartOrders> orders=ordersService.getAllCartByOrdersId(orderId);
        if (orders.isEmpty()){
            model.addAttribute("error","empty");
        }else{
            model.addAttribute("info",orders);
        }
        return "ViewCartOrdersOrders";
    }

    //redirect to update cart order
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/UpdateCartOrderPage/{cartOrdersId}")
    public String updateCartOrderPage(@PathVariable(name = "cartOrdersId")Integer cartOrdersId, Model model){
       try {
           CartOrders co = ordersService.getByCartOrdersId(cartOrdersId);
           model.addAttribute("info", co);
           model.addAttribute("UpdateCartOrder",new CartOrdersDTO());
       }catch (Exception e){
           model.addAttribute("error", "empty");
       }
       return "UpdateCartOrders";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/UpdateCartOrders")
    public String updateCartOrder(@ModelAttribute("updateInfo")CartOrdersDTO cartOrders,Model model){
        ResponseEntity<?> o=ordersService.updateOrderStatus(cartOrders.getCartOrdersId(),cartOrders);
        if (o.getStatusCodeValue()==200){
            model.addAttribute("success","cart order updated");
        }else{
            model.addAttribute("error","unable to update");
        }
        return "ViewCartOrders";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/MyCartOrders")
    public String getMyOrders(HttpServletRequest request,Model model){
        List<CartOrders> co=ordersService.getAllCartOrdersByUserId(request);
        if (co.isEmpty()){
            model.addAttribute("success","empty");
        }else{
            model.addAttribute("info",co);
        }
        return "ViewMyCartOrders";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/AllPendingOrders")
    public String viewAllPendingOrders(Model model){
        List<Orders> orders=ordersService.getAllPendingOrdersByStatus("pending");
        if (orders.isEmpty()){
            model.addAttribute("success","empty");
        }else{
            model.addAttribute("info",orders);
        }
        return "ViewAllOrders";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/Order/{orderId}")
    public String viewOrder(@PathVariable(name = "orderId")Integer orderId ,Model model){
        try {
            Orders orders = ordersService.getAOrderById(orderId);
            model.addAttribute("info", orders);
        }catch (Exception e){
        model.addAttribute("error","empty");
        }
        return "ViewAOrder";
    }

}





















