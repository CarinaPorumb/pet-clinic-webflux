package com.example.petClinic.service;

import com.example.petClinic.dto.VetDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VetService {

    Flux<VetDTO> listVets();

    Mono<VetDTO> getVetById(Integer id);

    Mono<VetDTO> saveNewVet(VetDTO dto);

    Mono<VetDTO> updateVet(VetDTO dto, Integer id);

    Mono<Void> deleteVet(Integer id);

    Mono<VetDTO> patchVet(VetDTO dto, Integer id);

}