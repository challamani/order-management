package com.restaurant.dinehouse.repository;


import com.restaurant.dinehouse.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserId(String userId);
    Iterable<User> findAll();
}
