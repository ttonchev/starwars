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
import com.spring.model.Starship;
import com.spring.service.StarshipService;

@RestController
public class StarshipController {

	@Autowired
	private StarshipService starshipService;
	
	@GetMapping("/starships")
	public List<Starship> getAllStarships(){
		return starshipService.list();
	}

	@GetMapping(value = "/starships/search")
	public Starship findByName(@RequestParam(value = "name", required = true) String name) throws RestClientException {
		return starshipService.findByName(name);
	}
	@PatchMapping("/starships/{id}")
	public Starship updateStarshipCount(@PathVariable Long id,
			@Valid @RequestBody StarshipsCounterRequest starshipsCounterRequest) {
		Starship starship;
		switch (starshipsCounterRequest.action) {
			case INCREMENT:
				starship = starshipService.incrementCount(id, starshipsCounterRequest.count);
				break;
			case DECREMENT:
				starship = starshipService.decrementCount(id, starshipsCounterRequest.count);
				break;
			case SET:
				starship = starshipService.updateCount(id, starshipsCounterRequest.count);
				break;
			default:
				throw new ActionNotSupportedException(String.format("Action %s not supported", starshipsCounterRequest.action));
		}
		return starship;
	}

	public static class StarshipsCounterRequest {
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

		public StarshipsCounterRequest() {}

		public StarshipsCounterRequest(Action action, Integer count) {
			this.action = action;
			this.count = count;
		}
	}
}
