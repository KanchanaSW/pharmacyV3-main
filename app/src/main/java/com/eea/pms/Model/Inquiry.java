package com.eea.pms.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry {
    private Integer inquiryId;

    private String username;
    private String itemName;

    private String question;
    private String answer;
    private String date;
    private String rep;

    public Inquiry(String question) {
        this.question=question;
    }
    // private int itemId;
}
