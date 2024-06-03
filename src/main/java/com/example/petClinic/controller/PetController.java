package com.example.petClinic.controller;

import com.example.petClinic.dto.PetDTO;
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
@RequestMapping("/api/v2/pet")
public class PetController {

    private final PetService petService;

    @GetMapping
    public Flux<PetDTO> listPets() {
        return petService.listPets();
    }

    @GetMapping("/{id}")
    public Mono<PetDTO> getPetById(@PathVariable("id") Integer id) {
        return petService.getPetById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found")));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createNewPet(@Validated @RequestBody PetDTO dto) {
        return petService.saveNewPet(dto)
                .map(savedDTO -> ResponseEntity.created(
                                UriComponentsBuilder.fromPath("/api/v2/pet/{id}")
                                        .buildAndExpand(savedDTO.getId())
                                        .toUri())
                        .build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<PetDTO>> updatePet(@Validated @RequestBody PetDTO dto, @PathVariable("id") Integer id) {
        return petService.updatePet(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found")))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePet(@PathVariable("id") Integer id) {
        return petService.getPetById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found")))
                .flatMap(petDto -> petService.deletePet(petDto.getId()))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<PetDTO>> patchPet(@Validated @RequestBody PetDTO dto, @PathVariable("id") Integer id) {
        return petService.patchPet(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found")))
                .map(ResponseEntity::ok);
    }

}