package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.OrderedItems;
import com.pharmacy.v3.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private Integer ordersDTOId;

    private String date;
    private String status;
    private String city;
    private String address;
    private double total;
    private String cusName;

    private User user;
    private List<OrderedItems> orderedItems;
    private List<Item> itemList;
    private Integer[] list;
}
