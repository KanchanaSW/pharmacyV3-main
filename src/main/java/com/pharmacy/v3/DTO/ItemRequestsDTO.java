package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.User;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class ItemRequestsDTO {
    private Integer itemRequestsId;

    private String newItemName;
    private String note;

    private User user;


    public ItemRequestsDTO() {
    }

    public ItemRequestsDTO(String newItemName, String note, User user) {
        this.newItemName = newItemName;
        this.note = note;
        this.user = user;

    }

    public ItemRequestsDTO(Integer itemRequestsId, String newItemName, String note) {
        this.itemRequestsId=itemRequestsId;
        this.newItemName = newItemName;
        this.note = note;
    }


    public Integer getItemRequestsId() {
        return itemRequestsId;
    }

    public void setItemRequestsId(Integer itemRequestsId) {
        this.itemRequestsId = itemRequestsId;
    }

    public String getNewItemName() {
        return newItemName;
    }

    public void setNewItemName(String newItemName) {
        this.newItemName = newItemName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
