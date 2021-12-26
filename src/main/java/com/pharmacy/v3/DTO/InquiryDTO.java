package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {
    private Integer inquiryId;

    private User user;
    private Item item;

    private String question;
    private String answer;
    private String date;
    private boolean isReplied;
    private int itemId;

    private String username;
    private String itemName;
    private String rep;
}
