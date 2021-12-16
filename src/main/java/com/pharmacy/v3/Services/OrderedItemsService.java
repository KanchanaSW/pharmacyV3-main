package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.OrderedItems;
import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Repositories.OrderedItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderedItemsService {

    @Autowired
    private OrderedItemsRepository orderedItemsRepository;

    public List<OrderedItems> getAll(){return orderedItemsRepository.findAll();}

    public OrderedItems get(int orderedItemsId){return orderedItemsRepository.findById(orderedItemsId).get();}

    public OrderedItems save(OrderedItems orderedItems){return orderedItemsRepository.save(orderedItems);}

    public List<OrderedItems> getOrderedItemsByOrder(Orders orders){return orderedItemsRepository.findByOrders(orders);}

    public void delete(OrderedItems orderedItems){
        orderedItemsRepository.delete(orderedItems);
    }
}
