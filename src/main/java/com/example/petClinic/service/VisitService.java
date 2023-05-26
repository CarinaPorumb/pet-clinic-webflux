package com.example.petClinic.service;

import com.example.petClinic.model.VisitDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VisitService {

    Flux<VisitDTO> listVisits();

    Mono<VisitDTO> getVisitById(Integer id);

    Mono<VisitDTO> saveNewVisit(VisitDTO dto);

    Mono<VisitDTO> updateVisit(VisitDTO dto, Integer id);

    Mono<Void> deleteVisit(Integer id);

    Mono<VisitDTO> patchVisit(VisitDTO dto, Integer id);

}