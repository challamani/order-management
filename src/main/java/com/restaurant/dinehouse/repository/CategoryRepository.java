package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
