package com.example.petClinic.controller;

import com.example.petClinic.model.PetDTO;
import com.example.petClinic.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class PetController {

    public static final String PET_PATH = "/api/v2/pet";
    public static final String PET_PATH_ID = PET_PATH + "/{id}";

    private final PetService petService;

    @GetMapping(value = PET_PATH)
    Flux<PetDTO> listPets() {
        return petService.listPets();
    }

    @GetMapping(value = PET_PATH_ID)
    Mono<PetDTO> getPetById(@PathVariable("id") Integer id) {
        return petService.getById(id);
    }

    @PostMapping(value = PET_PATH)
    Mono<ResponseEntity<Void>> createNewPet(@RequestBody PetDTO dto) {
        return petService.saveNewPet(dto)
                .map(savedDTO -> ResponseEntity.created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/" + PET_PATH + "/" + savedDTO.getId())
                        .build().toUri()
                ).build());
    }

}