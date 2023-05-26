package com.example.petClinic.controller;

import com.example.petClinic.model.PetDTO;
import com.example.petClinic.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
        return petService.getById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(value = PET_PATH)
    Mono<ResponseEntity<Void>> createNewPet(@Validated @RequestBody PetDTO dto) {
        return petService.saveNewPet(dto)
                .map(savedDTO -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8082/" + PET_PATH + "/" + savedDTO.getId())
                                .build().toUri())
                        .build());
    }

    @PutMapping(value = PET_PATH_ID)
    Mono<ResponseEntity<PetDTO>> updatePet(@Validated @RequestBody PetDTO dto, @PathVariable Integer id) {
        return petService.updatePet(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedPet -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(value = PET_PATH_ID)
    Mono<ResponseEntity<Void>> deletePet(@PathVariable Integer id) {
        return petService.getById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(petDto -> petService.deletePet(petDto.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping(value = PET_PATH_ID)
    Mono<ResponseEntity<PetDTO>> patchPet(@Validated @RequestBody PetDTO dto, @PathVariable Integer id) {
        return petService.patchPet(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedPet -> ResponseEntity.noContent().build());
    }

}