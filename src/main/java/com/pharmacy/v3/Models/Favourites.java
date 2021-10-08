package com.pharmacy.v3.Models;

import javax.persistence.*;

@Entity
public class Favourites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favouritesId;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "item",referencedColumnName = "itemId")
    private Item item;

    public Favourites(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public Favourites() {
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
