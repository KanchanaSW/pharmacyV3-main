package com.pharmacy.v3.Models;

import javax.persistence.*;

@Entity
public class ItemRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemRequestsId;

    private String newItemName;
    private String note;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user")
    private User user;


    public ItemRequests() {
    }

    public ItemRequests(String newItemName, String note, User user) {
        this.newItemName = newItemName;
        this.note = note;
        this.user = user;

    }

    public ItemRequests(Integer itemRequestsId, String newItemName, String note) {
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
