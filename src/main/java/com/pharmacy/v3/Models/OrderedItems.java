package com.pharmacy.v3.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class OrderedItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderedItemsId;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "item", referencedColumnName = "itemId")
    private Item item;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "orders", referencedColumnName = "ordersId")
    private Orders orders;

    private int quantity;
    private double total;
    private Date date;
}
