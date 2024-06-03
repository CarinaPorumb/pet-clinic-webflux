package com.example.petClinic.service;

import com.example.petClinic.dto.PetDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetService {

    Flux<PetDTO> listPets();

    Mono<PetDTO> getPetById(Integer id);

    Mono<PetDTO> saveNewPet(PetDTO dto);

    Mono<PetDTO> updatePet(PetDTO dto, Integer id);

    Mono<Void> deletePet(Integer id);

    Mono<PetDTO> patchPet(PetDTO dto, Integer id);

}