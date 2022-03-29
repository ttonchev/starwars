package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.StarshipNotFoundException;
import com.spring.model.Vehicle;
import com.spring.repository.VehicleRepository;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
	@Autowired
	private SequenceGeneratorService sequenceGenerator;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private SwapiService swapiService;

	@Override
	public List<Vehicle> list() {
		return vehicleRepository.findAll();
	}

	@Override
	public Vehicle findByName(final String name) {
		Vehicle vehicleWithCount = vehicleRepository.findByName(name);
		if (vehicleWithCount == null) {
			Vehicle vehicle = swapiService.getVehicleByName(name);
			Vehicle newVehicle = new Vehicle(vehicle.getName(), 1);
			newVehicle.setId(sequenceGenerator.generateSequence(Vehicle.SEQUENCE_NAME));
			vehicleRepository.save(newVehicle);
			vehicleWithCount = new Vehicle(newVehicle.getName(), newVehicle.getCount());
		}
		return vehicleWithCount;
	}

	@Override
	public Vehicle incrementCount(final Long id, final Integer count) {
		Vehicle vehicle =
				vehicleRepository.findById(id).orElseThrow(() -> new StarshipNotFoundException(
						String.format("Vehicle with id %d not found",	id)));
		vehicle.setCount(vehicle.getCount() + count);
		vehicleRepository.save(vehicle);
		return vehicle;
	}

	@Override
	public Vehicle decrementCount(final Long id, final Integer count) {
		Vehicle vehicle =
				vehicleRepository.findById(id).orElseThrow(() -> new StarshipNotFoundException(
						String.format("Vehicle with id %d not found",	id)));
		vehicle.setCount(Math.max(vehicle.getCount() - count, 0));
		vehicleRepository.save(vehicle);
		return vehicle;
	}

	@Override
	public Vehicle updateCount(final Long id, final Integer count) {
		Vehicle vehicle =
				vehicleRepository.findById(id).orElseThrow(() -> new StarshipNotFoundException(
						String.format("Vehicle with id %d not found",	id)));
		vehicle.setCount(count);
		vehicleRepository.save(vehicle);
		return vehicle;
	}
}

