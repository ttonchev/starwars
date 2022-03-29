package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.spring.exception.StarshipNotFoundException;
import com.spring.exception.VehicleNotFoundException;
import com.spring.model.Starship;
import com.spring.model.SwapiStarship;
import com.spring.model.SwapiVehicle;
import com.spring.model.Vehicle;

@Service
public class SwapiServiceImpl implements SwapiService {
  @Autowired
  private RestTemplate restTemplate;

  @Value("${swapi.BASE_URL:}")
  private String BASE_URL;

  @Override
  public Starship getStarshipByName(final String name) throws RestClientException, StarshipNotFoundException {
    SwapiStarship starship = restTemplate.getForObject(BASE_URL + "/starships?search=" + name,
        SwapiStarship.class);
    return starship.getResults().stream()
        .filter(s -> s.getName().equals(name)).findFirst()
        .orElseThrow(() -> new StarshipNotFoundException("No such starship on the star wars api, check the name "
            + "you've input"));
  }

  @Override
  public Vehicle getVehicleByName(final String name) throws RestClientException, VehicleNotFoundException {
    SwapiVehicle vehicle = restTemplate.getForObject(BASE_URL + "/vehicles?search=" + name,
        SwapiVehicle.class);
    return vehicle.getResults().stream()
        .filter(v -> v.getName().equals(name)).findFirst()
        .orElseThrow(() -> new VehicleNotFoundException("No such vehicle on the star wars api, check the name you've"
            + " input"));
  }
}
