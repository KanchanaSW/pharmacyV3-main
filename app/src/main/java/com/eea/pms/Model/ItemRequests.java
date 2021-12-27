package com.eea.pms.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequests {
    private Integer itemRequestsId;
    private String newItemName;
    private String note;
    private Integer userId;
    private String username;
    private String status;

    public ItemRequests(Integer itemRequestsId, String note) {
        this.itemRequestsId = itemRequestsId;
        this.note = note;
    }

    public ItemRequests(String newItemName, String note) {
        this.newItemName = newItemName;
        this.note = note;
    }
}
