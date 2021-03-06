package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.Inquiry;
import com.pharmacy.v3.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Integer> {
    List<Inquiry> findByItemItemId(Integer itemId);
    List<Inquiry> findByIsReplied(boolean isReplied);

   // @Query("SELECT i FROM Inquiry i WHERE i.user like ?1%")
    List<Inquiry> findByUserUserId(Integer userId);
}
