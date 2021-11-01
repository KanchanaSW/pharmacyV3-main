package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;

import javax.persistence.*;

public class FavouritesDTO {
    private Integer favouritesId;
    private User user;
    private Item item;

    public FavouritesDTO(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public FavouritesDTO() {
    }

    public Integer getFavouritesId() {
        return favouritesId;
    }

    public void setFavouritesId(Integer favouritesId) {
        this.favouritesId = favouritesId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
