package com.eea.pms.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private Integer itemId;
    private String itemName;
    private String des;
    private double price;
    private int quantity;
    private String categoryName;


    public Item(String iName, String iDes, String iCatName, int iq, double ip) {
        this.itemName=iName;
        this.des=iDes;
        this.categoryName=iCatName;
        this.quantity=iq;
        this.price=ip;
    }
}
