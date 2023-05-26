package com.example.petClinic.repository;

import com.example.petClinic.domain.Owner;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OwnerRepository extends ReactiveCrudRepository<Owner, Integer> {

}