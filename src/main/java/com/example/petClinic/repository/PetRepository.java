package com.example.petClinic.repository;

import com.example.petClinic.domain.Pet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PetRepository extends ReactiveCrudRepository<Pet, Integer> {

}