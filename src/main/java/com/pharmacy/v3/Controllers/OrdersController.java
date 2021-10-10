package com.pharmacy.v3.Controllers;

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
    public ResponseEntity<MessageResponse> addCartOrders(@RequestBody CartOrders newOrders, HttpServletRequest request) {
        return ordersService.addCartOrders(newOrders, request);
    }

    /* add order
    {"total": 1000,"status": true,"date": "06/10/2021","city": "Rukmale", "address": "235/10 New Town" }
    */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/orders/add-order")
    public ResponseEntity<?> addOrder(@RequestBody Orders orders, HttpServletRequest request) {
        return ordersService.addOrder(orders, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/status/{orderStatus}/user/{userId}")
    public List<Orders> getAllUserOrdersByStatus(@PathVariable Integer userId, @PathVariable String orderStatus, HttpServletRequest request) {
        return ordersService.getAllUserOrders(userId, orderStatus, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cart/{ordersId}")
    public List<CartOrders> getAllCartByOrdersId(@PathVariable Integer ordersId, HttpServletRequest request) {
        return ordersService.getAllCartByOrdersId(ordersId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/order-update/{cartOrdersId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Integer cartOrdersId, @RequestBody CartOrders updateCartOrders, HttpServletRequest request) {
        return ordersService.updateOrderStatus(cartOrdersId, updateCartOrders);
    }

    /////////
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/order-status/{ordersId}/{status}")
    public ResponseEntity<?> updateOrderStatusByOrdersId(@PathVariable Integer ordersId, @PathVariable String status) {
        return ordersService.updateOrderStatusByOrdersId(ordersId, status);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/cart-orders/{userId}")
    public List<CartOrders> getAllCartOrdersByUserId(@PathVariable Integer userId, HttpServletRequest request) {
        return ordersService.getAllCartOrdersByUserId(userId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/{status}")
    public List<Orders> getAllPendingOrdersByStatus(@PathVariable String status, HttpServletRequest request) {
        return ordersService.getAllPendingOrdersByStatus(status);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/order/{orderId}")
    public Orders getAOrderById(@PathVariable Integer orderId) {
        return ordersService.getAOrderById(orderId);
    }


}
