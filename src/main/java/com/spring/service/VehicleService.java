package com.spring.service;

import java.util.List;

import com.spring.model.Vehicle;

public interface VehicleService {
	List<Vehicle> list();
	Vehicle findByName(String name);

	Vehicle incrementCount(Long id, Integer count);

	Vehicle decrementCount(Long id, Integer count);

	Vehicle updateCount(Long id, Integer count);
}
