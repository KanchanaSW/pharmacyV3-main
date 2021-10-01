package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ItemCatgRepo extends JpaRepository<ItemCategory,Integer> {
    List<ItemCategory> findByItemCategoryId(Integer itemCategoryId);
    boolean existsByItemCategoryId(Integer itemCategoryId);

    @Modifying
    @Transactional
    @Query( value = "DELETE FROM item_category WHERE item = ?1", nativeQuery = true)
    void deleteItemCategory(Integer itemId);
}
