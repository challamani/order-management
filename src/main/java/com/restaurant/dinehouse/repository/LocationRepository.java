package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {
}
