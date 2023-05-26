package com.example.petClinic.repository;

import com.example.petClinic.domain.Vet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VetRepository extends ReactiveCrudRepository<Vet, Integer> {

}