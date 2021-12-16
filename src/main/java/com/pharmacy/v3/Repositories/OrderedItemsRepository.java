package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.OrderedItems;
import com.pharmacy.v3.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedItemsRepository extends JpaRepository<OrderedItems,Integer> {
    List<OrderedItems> findByOrders(Orders orders);

}
