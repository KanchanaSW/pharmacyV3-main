package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.DTO.OrderDTO;
import com.pharmacy.v3.DTO.OrderedItemsDTO;
import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.OrderedItems;
import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Services.CartService;
import com.pharmacy.v3.Services.OrderedItemsService;
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
    private OrderedItemsService orderedItemsService;
    @Autowired
    private CartService cartService;



    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/PlaceNewOrder")
    public String addNewOrder(@ModelAttribute("newOrder") Orders orders, HttpServletRequest request, Model model) {

    /*    ResponseEntity<?> nOrder = ordersService.addOrder(request, orders);
        if (nOrder.getStatusCodeValue() == 200) {
            model.addAttribute("success", "Order added success");
        } else {
            model.addAttribute("error", "cannont add order");
        }*/
        return "redirect:/OrdersView";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/OrdersView")
    public String ordersView(Model model) {
        try {
            List<OrderDTO> allItems = ordersService.getAll();
            model.addAttribute("info", allItems);
        } catch (Exception e) {
            model.addAttribute("error", "empty");
        }
        return "OrdersView";
    }

        //view order page
        @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
        @GetMapping(value = "/ViewOrderedItems/{ordersDTOId}")
        public String viewOrderds(@PathVariable(name = "ordersDTOId")int ordersDTOId, Model model){
            try{
                Orders orders=ordersService.get(ordersDTOId);
                List<OrderedItemsDTO> list=orderedItemsService.getOrderedItemsByOrder(orders);
                model.addAttribute("oItem",list);
            }catch (Exception e){
                model.addAttribute("error" ,"empty");
            }
            return "OrderedItemView";
        }
}
