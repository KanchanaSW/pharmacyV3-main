package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    @Override
    List<Item> findAllById(Iterable<Integer> integers);
    Boolean existsByItemName(String itemName);

    @Query("FROM Item i where i.itemName like %:itemName%")
    List<Item> name(String itemName);
}
