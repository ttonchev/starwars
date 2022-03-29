package com.spring.service;

import org.springframework.web.client.RestClientException;

import com.spring.exception.StarshipNotFoundException;
import com.spring.exception.VehicleNotFoundException;
import com.spring.model.Starship;
import com.spring.model.Vehicle;

public interface SwapiService {
  Starship getStarshipByName(String name) throws RestClientException, StarshipNotFoundException;
  Vehicle getVehicleByName(String name) throws RestClientException, VehicleNotFoundException;
}
