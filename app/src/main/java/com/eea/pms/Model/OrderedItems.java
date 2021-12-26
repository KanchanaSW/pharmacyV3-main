package com.eea.pms.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedItems {
    private int orderedItemDTOId;
    private String itemName;
    private int quantity;
    private double total;
    private String  date;
}
