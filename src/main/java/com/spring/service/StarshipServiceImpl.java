package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.StarshipNotFoundException;
import com.spring.model.Starship;
import com.spring.repository.StarshipRepository;

@Service
@Transactional
public class StarshipServiceImpl implements StarshipService{
	@Autowired
	private SequenceGeneratorService sequenceGenerator;
	@Autowired
	private StarshipRepository starshipRepository;
	@Autowired
	private SwapiService swapiService;

	@Override
	public List<Starship> list() {
		return starshipRepository.findAll();
	}

	@Override
	public Starship findByName(final String name) {
		Starship starshipWithCount = starshipRepository.findByName(name);
		if (starshipWithCount == null) {
			Starship starship = swapiService.getStarshipByName(name);
			Starship newStarship = new Starship(starship.getName(), 1);
			newStarship.setId(sequenceGenerator.generateSequence(Starship.SEQUENCE_NAME));
			starshipRepository.save(newStarship);
			return newStarship;
		}
		return starshipWithCount;
	}

	@Override
	public Starship incrementCount(final Long id, final Integer count) {
		Starship starship =
				starshipRepository.findById(id).orElseThrow(() -> new StarshipNotFoundException(
						String.format("Starship with id %d not found",	id)));
		starship.setCount(starship.getCount() + count);
		starshipRepository.save(starship);
		return starship;
	}

	@Override
	public Starship decrementCount(final Long id, final Integer count) {
		Starship starship =
				starshipRepository.findById(id).orElseThrow(() -> new StarshipNotFoundException(
						String.format("Starship with id %d not found",	id)));
		starship.setCount(Math.max(starship.getCount() - count, 0));
		starshipRepository.save(starship);
		return starship;
	}

	@Override
	public Starship updateCount(final Long id, final Integer count) {
		Starship starship =
				starshipRepository.findById(id).orElseThrow(() -> new StarshipNotFoundException(
						String.format("Starship with id %d not found",	id)));
		starship.setCount(count);
		starshipRepository.save(starship);
		return starship;
	}
}

