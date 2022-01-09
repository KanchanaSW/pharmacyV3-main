package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.OrderDTO;
import com.pharmacy.v3.Models.*;
import com.pharmacy.v3.Repositories.OrderedItemsRepository;
import com.pharmacy.v3.Repositories.OrdersRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderedItemsRepository orderedItemsRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderedItemsService orderedItemsService;

    public List<OrderDTO> getAll() {
        SimpleDateFormat ft =
                new SimpleDateFormat("yyyy.MM.dd");
        List<OrderDTO> alt = new ArrayList<>();
        for (Orders orders : ordersRepository.findAll()) {
            OrderDTO od = new OrderDTO();
            java.util.Date date = orders.getDate();

            od.setOrdersDTOId(orders.getOrdersId());
            od.setCusName(orders.getCusName());
            od.setCity(orders.getCity());
            od.setAddress(orders.getAddress());
            od.setDate(ft.format(date));
            od.setTotal(orders.getTotal());
            od.setStatus(orders.getStatus());
            od.setUser(orders.getUser());
            alt.add(od);
        }
        return alt;
    }

    public Orders get(int orderId) {
        return ordersRepository.findById(orderId).get();
    }

    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    public List<Orders> getByUserAndStatusList(HttpServletRequest request, String status) {
        String userName = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(userName).get();
        return ordersRepository.findByUserAndStatus(user, status);
    }

    //admin function
    public List<Orders> getAllPendingOrdersByStatus(String status) {
        return ordersRepository.findByStatus(status);
    }

    public List<Orders> getAllUserOrders(HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(userName).get();
        return ordersRepository.findByUser(user);
    }

    public String cancel(int orderId) {
        Orders orders = get(orderId);
        orders.setStatus("cancelled");
        save(orders);
        return "cancelled";
    }

    public String pay(int orderId) {
        Orders orders = get(orderId);
        orders.setStatus("paid");
        save(orders);
        return "paid";
    }

    public String delete(int orderId) {
        try {
            Orders orders = get(orderId);
            List<OrderedItems> oiList = orders.getOrderedItems();

            for (OrderedItems orderedItems : oiList) {
                OrderedItems oi = orderedItemsService.get(orderedItems.getOrderedItemsId());
                int quant = oi.getQuantity();
                Item item = itemService.find(oi.getItem().getItemId());
                int itemQuant = item.getQuantity();
                item.setQuantity(itemQuant + quant);
                itemService.save(item);

                orderedItemsService.delete(oi);
            }
            ordersRepository.delete(orders);
            return "deleted";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public List<Orders> all() {
        return ordersRepository.findAll();
    }

    public void deleteOrder(Orders orders) {
        ordersRepository.delete(orders);
    }

    public ResponseEntity<?> addOrder(HttpServletRequest request, Orders newOrder) {
        try {
            String userName = request.getUserPrincipal().getName();
            User user = userRepository.findByUsername(userName).get();//get the user
            Integer[] cartList;
            cartList = newOrder.getList();//get the item ids
            Date date = new Date();//get the order date.

            Orders orders = new Orders();
            orders.setUser(user);
            orders.setDate(date);
            orders.setCusName(newOrder.getCusName());
            orders.setTotal(newOrder.getTotal());
            orders.setStatus(newOrder.getStatus());
            orders.setCity(newOrder.getCity());
            orders.setAddress(newOrder.getAddress());
            orders = ordersRepository.save(orders);

            for (int x = 0; x < cartList.length; x++) {
                Cart cart = cartService.getCartFromId(cartList[x]);//get single cart
                Item item = itemService.find(cart.getItem().getItemId());
                int itemQuantity = item.getQuantity();
                item.setQuantity(itemQuantity - cart.getQuantity());//update item q
                item = itemService.save(item);

                OrderedItems oi = new OrderedItems();
                oi.setItem(item);
                oi.setOrders(orders);
                oi.setDate(date);
                oi.setQuantity(cart.getQuantity());
                oi.setTotal(cart.getTotal());
                orderedItemsRepository.save(oi);

                cartService.deleteCart(cart.getCartId());
            }

            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("exception error" + e);
        }
    }

}
