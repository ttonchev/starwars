package com.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.Starship;

@Repository
public interface StarshipRepository extends MongoRepository<Starship, Long> {
  Starship findByName(String name);
}
