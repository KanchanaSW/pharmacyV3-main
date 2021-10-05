package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    Cart findByItemItemIdAndUserUserId(Integer itemId, Integer userId);

    boolean existsByUserAndItemAndIsPurchased(User user, Item item,  boolean isPurchased);

    boolean existsByItemItemIdAndUserUserId(Integer itemId,  Integer userId);

    Cart findByUserAndItem(User user, Item item);

    List<Cart> findByUserAndIsPurchased(User user, boolean isPurchased);

    List<Cart> findByUserAndIsPurchasedOrderByCartIdDesc(User user, boolean isPurchased);

    @Modifying
    @Transactional
    @Query( value = "DELETE FROM cart WHERE user = ?1 and item =?2", nativeQuery = true)
    void deleteItemfromCart(Integer userId,Integer itemId);

}
