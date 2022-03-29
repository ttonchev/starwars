package com.spring.service;

import java.util.List;

import com.spring.model.Starship;

public interface StarshipService {
	List<Starship> list();
	Starship findByName(String name);

  Starship incrementCount(Long id, Integer count);

	Starship decrementCount(Long id, Integer count);

	Starship updateCount(Long id, Integer count);
}
