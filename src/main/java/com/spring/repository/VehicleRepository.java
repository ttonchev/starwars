package com.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.Vehicle;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, Long> {
  Vehicle findByName(String name);
}
