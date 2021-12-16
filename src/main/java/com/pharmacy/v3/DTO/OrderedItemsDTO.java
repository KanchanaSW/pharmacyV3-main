package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderedItemsDTO {
    private int orderedItemDTOId;
    private Item item;
    private Orders order;

    private int quantity;
    private double total;
    private Date date;
}
