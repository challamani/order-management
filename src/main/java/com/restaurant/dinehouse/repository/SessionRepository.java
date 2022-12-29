package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.AgentSession;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<AgentSession, Long> {
    @Override
    Iterable<AgentSession> findAll();
}
