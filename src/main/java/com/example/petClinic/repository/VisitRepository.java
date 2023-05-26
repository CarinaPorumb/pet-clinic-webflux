package com.example.petClinic.repository;

import com.example.petClinic.domain.Visit;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VisitRepository extends ReactiveCrudRepository<Visit, Integer> {

}