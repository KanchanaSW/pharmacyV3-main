package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.Favourites;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites,Integer> {

    List<Favourites> findByUserOrderByFavouritesIdDesc(User user);

    Favourites findByItem(Item item);

    boolean existsByUser(User user);

    boolean existsByItem(Item item);

    Favourites findByItemAndUser(Item item, User user);
}
