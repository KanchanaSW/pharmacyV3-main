package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.OrderedItemsDTO;
import com.pharmacy.v3.Models.OrderedItems;
import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Repositories.OrderedItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderedItemsService {

    @Autowired
    private OrderedItemsRepository orderedItemsRepository;

    public List<OrderedItems> getAll(){return orderedItemsRepository.findAll();}

    public OrderedItems get(int orderedItemsId){return orderedItemsRepository.findById(orderedItemsId).get();}

    public OrderedItems save(OrderedItems orderedItems){return orderedItemsRepository.save(orderedItems);}

    public List<OrderedItemsDTO> getOrderedItemsByOrder(Orders orders){
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy.MM.dd");
        List<OrderedItemsDTO> alt=new ArrayList<>();
        for (OrderedItems orderedItems:orderedItemsRepository.findByOrders(orders)){
            OrderedItemsDTO od=new OrderedItemsDTO();
            java.util.Date date=orderedItems.getDate();
            od.setOrderedItemDTOId(orderedItems.getOrderedItemsId());
            od.setQuantity(orderedItems.getQuantity());
            od.setTotal(orderedItems.getTotal());
            od.setItemName(orderedItems.getItem().getItemName());
            od.setDate(ft.format(date));
            alt.add(od);
        }

        return alt;
    }

    public void delete(OrderedItems orderedItems){
        orderedItemsRepository.delete(orderedItems);
    }
}
