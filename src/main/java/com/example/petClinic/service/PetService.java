package com.example.petClinic.service;

import com.example.petClinic.model.PetDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetService {

    Flux<PetDTO> listPets();

    Mono<PetDTO> getById(Integer id);

    Mono<PetDTO> saveNewPet(PetDTO dto);

}