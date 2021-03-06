package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.CartDTO;
import com.pharmacy.v3.DTO.InquiryDTO;
import com.pharmacy.v3.DTO.OrderDTO;
import com.pharmacy.v3.DTO.OrderedItemsDTO;
import com.pharmacy.v3.Models.Inquiry;
import com.pharmacy.v3.Models.OrderedItems;
import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Services.OrderedItemsService;
import com.pharmacy.v3.Services.OrdersService;
import com.pharmacy.v3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/orders")
@RestController
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderedItemsService orderedItemsService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/addNewOrder")
    public ResponseEntity<?> addNewOrder(@RequestBody Orders orders, HttpServletRequest request) {
        return ordersService.addOrder(request, orders);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/cancelOrder/{ordersId}")
    public ResponseEntity<?> cancelOrder(@PathVariable int ordersId) {
        String st = ordersService.cancel(ordersId);
        return ResponseEntity.ok(st);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(value = "/deleteOrder/{ordersId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int ordersId) {
        String st = ordersService.delete(ordersId);
        return ResponseEntity.ok(st);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/viewAllMyOrders")
    public ResponseEntity<?> viewAllMyOrders(HttpServletRequest request) {
        List<Orders> list = ordersService.getAllUserOrders(request);
        if (list.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty");
        }
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/viewMyPendingOrders")
    public ResponseEntity<?> viewMyPendingOrders(HttpServletRequest request) {
        List<Orders> list = ordersService.getByUserAndStatusList(request, "pending");
        if (list.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty");
        }
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/viewSingleOrder/{ordersDTOId}")
    public ResponseEntity<?> viewSingleOders(@PathVariable int ordersDTOId) {
        Orders orders = ordersService.get(ordersDTOId);
        List<OrderedItemsDTO> list = orderedItemsService.getOrderedItemsByOrder(orders);
        if (list.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty");
        }
        return ResponseEntity.ok(list);
    }

    //view all pending orders admin function
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/allOrders")
    public ResponseEntity<?> allOrders(HttpServletRequest request) {
        List<OrderDTO> alt = new ArrayList<>();
        for (OrderDTO orders : ordersService.getAll()) {
            OrderDTO od = new OrderDTO();
            od.setOrdersDTOId(orders.getOrdersDTOId());
            od.setCusName(orders.getCusName());
            od.setCity(orders.getCity());
            od.setAddress(orders.getAddress());
            od.setDate(orders.getDate());
            od.setTotal(orders.getTotal());
            od.setStatus(orders.getStatus());
            od.setUsername(orders.getUser().getUsername());
            alt.add(od);
        }
        System.out.println(alt);
        return ResponseEntity.ok(alt);
    }

}
