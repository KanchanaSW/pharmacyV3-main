package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.CartOrdersDTO;
import com.pharmacy.v3.DTO.OrdersDTO;
import com.pharmacy.v3.Models.*;
import com.pharmacy.v3.Repositories.*;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {
    private OrdersRepository ordersRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private CartOrdersRepository cartOrdersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, UserRepository userRepository, ItemRepository itemRepository, CartRepository cartRepository, CartOrdersRepository cartOrdersRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartOrdersRepository = cartOrdersRepository;
    }

    public List<Orders> getAllUserOrders(String status, HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(userName).get();
        List<Orders> ordersList = ordersRepository.findByUserAndStatusOrderByOrdersIdDesc(user, status);
        return ordersList;
    }

    public List<Orders> getAllPendingOrdersByStatus(String status) {
        List<Orders> ordersList = ordersRepository.findByStatus(status);
        return ordersList;
    }

    public List<CartOrders> getAllCartByOrdersId(Integer ordersId) {
        Orders orders = ordersRepository.findById(ordersId).get();
        List<CartOrders> cartList = cartOrdersRepository.findByOrders(orders);
        return cartList;
    }

    public Orders getAOrderById(Integer orderId) {
        if (ordersRepository.existsById(orderId)) {
            Orders orders = ordersRepository.findById(orderId).get();
            return orders;
        }else {
            return null;
        }
    }

    public List<CartOrders> getAllCartOrdersByUserId(HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(userName).get();
        List<CartOrders> cartOrdersList = cartOrdersRepository.findByOrdersUserUserIdOrderByCartOrdersId(user.getUserId());
        return cartOrdersList;
    }

    public ResponseEntity<?> addOrder(OrdersDTO newOrder, HttpServletRequest request) {
        try {
            String userName = request.getUserPrincipal().getName();
            User user = userRepository.findByUsername(userName).get();
            Orders orders = new Orders();
            orders.setUser(user);
            orders.setTotal(newOrder.getTotal());
            orders.setStatus("pending");
            orders.setDate(newOrder.getDate());
            orders.setCity(newOrder.getCity());
            orders.setAddress(newOrder.getAddress());
            ordersRepository.save(orders);
            return ResponseEntity.ok().body(new MessageResponse("Success: Your orders are placed."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<MessageResponse> addCartOrders(CartOrdersDTO newCartOrders, HttpServletRequest request) {
        try {
            CartOrders cartOrders = new CartOrders();
            cartOrders.setOrders(newCartOrders.getOrders());
            cartOrders.setCart(newCartOrders.getCart());
            Item item = newCartOrders.getCart().getItem();
            item.setQuantity(newCartOrders.getCart().getItem().getQuantity() - newCartOrders.getCart().getQuantity());
            itemRepository.save(item);
            Cart cart = newCartOrders.getCart();
            cart.setPurchased(true);
            cartRepository.save(cart);
            cartOrdersRepository.save(cartOrders);
            return ResponseEntity.ok().body(new MessageResponse("Success: Your orders are placed."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
    public CartOrders getByCartOrdersId(Integer cartOrdersId){
        if (cartOrdersRepository.existsById(cartOrdersId)) {
            CartOrders cartOrders = cartOrdersRepository.findById(cartOrdersId).get();
            return cartOrders;
        }else{
            return null;
        }
    }

    public ResponseEntity<?> updateOrderStatus(Integer cartOrdersId, CartOrdersDTO updateCartOrders) {
        try {
            if (cartOrdersRepository.existsById(cartOrdersId)) {
                CartOrders cartOrders = cartOrdersRepository.findById(cartOrdersId).get();
                Item item = cartOrders.getCart().getItem();
                cartOrders.getOrders().setDate(updateCartOrders.getOrders().getDate());
                cartOrders.getOrders().setStatus(updateCartOrders.getOrders().getStatus());
                item.setQuantity(item.getQuantity() + updateCartOrders.getCart().getQuantity());
                itemRepository.save(item);
                cartOrdersRepository.save(cartOrders);
                return ResponseEntity.ok().body(cartOrders);
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Error: update Un successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<?> updateOrderStatusByOrdersId(Integer ordersId, String status) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String date = sdf.format(new Date());
            if (ordersRepository.existsById(ordersId)) {
                Orders orders = ordersRepository.findById(ordersId).get();
                orders.setStatus(status);
                orders.setDate(date);
                List<CartOrders> cartOrdersList = cartOrdersRepository.findByOrders(orders);
                for (CartOrders cartOrders : cartOrdersList) {
                    Item item = cartOrders.getCart().getItem();
                    item.setQuantity(item.getQuantity() + cartOrders.getCart().getQuantity());
                    itemRepository.save(item);
                    ordersRepository.save(orders);
                }
                return ResponseEntity.ok().body(new MessageResponse("Success: updated"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No order id found"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
}
