package com.eea.pms.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer ordersDTOId;
    private String date;
    private String status;
    private String city;
    private String address;
    private double total;
    private String cusName;
  //  private User user;
    private String username;
}
