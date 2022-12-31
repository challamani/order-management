package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.UserSession;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<UserSession, Long> {
    @Override
    Iterable<UserSession> findAll();

    void deleteByUserId(String userId);
}
