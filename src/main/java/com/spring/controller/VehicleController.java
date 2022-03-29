package com.spring.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.spring.exception.ActionNotSupportedException;
import com.spring.model.Vehicle;
import com.spring.service.VehicleService;

@RestController
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	@GetMapping("/vehicles")
	public List<Vehicle> getAllStarships(){
		return vehicleService.list();
	}

	@GetMapping(value = "/vehicles/search")
	public Vehicle findByName(@RequestParam(value = "name", required = true) String name) throws RestClientException {
		return vehicleService.findByName(name);
	}
	@PatchMapping("/vehicles/{id}")
	public Vehicle updateVehiclesCount(@PathVariable Long id, @Valid @RequestBody
			VehicleController.VehiclesCounterRequest vehiclesCounterRequest) {
		Vehicle vehicle;
		switch (vehiclesCounterRequest.action) {
			case INCREMENT:
				vehicle = vehicleService.incrementCount(id, vehiclesCounterRequest.count);
				break;
			case DECREMENT:
				vehicle = vehicleService.decrementCount(id, vehiclesCounterRequest.count);
				break;
			case SET:
				vehicle = vehicleService.updateCount(id, vehiclesCounterRequest.count);
				break;
			default:
				throw new ActionNotSupportedException(String.format("Action %s not supported", vehiclesCounterRequest.action));
		}
		return vehicle;
	}

	public static class VehiclesCounterRequest {
		@NotNull
		@Valid
		public Action action;
		@NotNull
		public Integer count;

		public enum Action {
			INCREMENT,
			DECREMENT,
			SET,
		}

		public VehiclesCounterRequest() {}

		public VehiclesCounterRequest(Action action, Integer count) {
			this.action = action;
			this.count = count;
		}
	}
}
