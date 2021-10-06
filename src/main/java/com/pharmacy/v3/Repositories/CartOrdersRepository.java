package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.CartOrders;
import com.pharmacy.v3.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartOrdersRepository extends JpaRepository<CartOrders,Integer> {

    List<CartOrders> findByOrders(Orders orders);
    List<CartOrders> findByOrdersUserUserIdOrderByCartOrdersId(Integer userId);
}
