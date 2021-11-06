package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.CartOrdersDTO;
import com.pharmacy.v3.DTO.OrdersDTO;
import com.pharmacy.v3.Models.CartOrders;
import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Response.MessageResponse;
import com.pharmacy.v3.Services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/orders")
@RestController
public class OrdersController {
    private OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/cart/add-order")
    public ResponseEntity<MessageResponse> addCartOrders(@RequestBody CartOrdersDTO newOrders, HttpServletRequest request) {
        return ordersService.addCartOrders(newOrders, request);
    }

    /* add order
    {"total": 1000,"status": "pending","date": "06/10/2021","city": "Rukmale", "address": "235/10 New Town" }
    */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/orders/add-order")
    public ResponseEntity<?> addOrder(@RequestBody OrdersDTO orders, HttpServletRequest request) {
        return ordersService.addOrder(orders, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/orders/user-orders")
    public List<Orders> getAllUserOrdersByStatus(HttpServletRequest request) {
        return ordersService.getAllUserOrders("pending",request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cart/{ordersId}")
    public List<CartOrders> getAllCartByOrdersId(@PathVariable Integer ordersId, HttpServletRequest request) {
        return ordersService.getAllCartByOrdersId(ordersId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/order-update")
    public ResponseEntity<?> updateOrderStatus( @RequestBody CartOrdersDTO updateCartOrders, HttpServletRequest request) {
        return ordersService.updateOrderStatus(updateCartOrders.getCartOrdersId(), updateCartOrders);
    }

    /////////
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/order-status/{ordersId}/{status}")
    public ResponseEntity<?> updateOrderStatusByOrdersId(@PathVariable Integer ordersId, @PathVariable String status) {
        return ordersService.updateOrderStatusByOrdersId(ordersId, status);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/cart-orders/{userId}")
    public List<CartOrders> getAllCartOrdersByUserId(HttpServletRequest request) {
        return ordersService.getAllCartOrdersByUserId(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allOrders")
    public List<Orders> getAllPendingOrdersByStatus() {
        return ordersService.getAllPendingOrdersByStatus("pending");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/order/{orderId}")
    public Orders getAOrderById(@PathVariable Integer orderId) {
        return ordersService.getAOrderById(orderId);
    }


}
