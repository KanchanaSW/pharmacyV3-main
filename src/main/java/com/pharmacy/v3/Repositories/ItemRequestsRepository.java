package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.ItemRequests;
import com.pharmacy.v3.Models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRequestsRepository extends JpaRepository<ItemRequests,Integer> {
    List<ItemRequests> findByUserOrderByItemRequestsIdDesc(User user);
    boolean existsByUser(User user);

    @Override
    boolean existsById(Integer integer);

}










