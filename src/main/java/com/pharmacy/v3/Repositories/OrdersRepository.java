package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.Orders;
import com.pharmacy.v3.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findByUser(User user);
    List<Orders> findByUserAndStatus(User user,String status);
    List<Orders> findByStatus(String status);
}
