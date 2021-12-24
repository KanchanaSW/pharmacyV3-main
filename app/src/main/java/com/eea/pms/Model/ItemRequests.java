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
}
