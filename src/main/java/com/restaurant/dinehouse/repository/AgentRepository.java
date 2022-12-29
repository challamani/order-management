package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends CrudRepository<Agent, Long> {
    Iterable<Agent> findAll();
}
