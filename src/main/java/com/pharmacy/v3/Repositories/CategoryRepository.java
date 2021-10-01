package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Integer> {

   Category categoryIs(String name);
   boolean existsByCategory(String categoryName);
   List<Category> findAllByOrderByCategoryIdDesc();
}
