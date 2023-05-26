package com.example.petClinic.controller;

import com.example.petClinic.model.VetDTO;
import com.example.petClinic.service.VetService;
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
public class VetController {

    public static final String VET_PATH = "/api/v2/vet";
    public static final String VET_PATH_ID = VET_PATH + "/{id}";

    private final VetService vetService;

    @GetMapping(value = VET_PATH)
    Flux<VetDTO> listVets() {
        return vetService.listVest();
    }

    @GetMapping(value = VET_PATH_ID)
    Mono<VetDTO> getVetById(@PathVariable Integer id) {
        return vetService.getVetById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(value = VET_PATH)
    Mono<ResponseEntity<Void>> createNewVet(@Validated @RequestBody VetDTO dto) {
        return vetService.saveNewVet(dto)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8082/" + VET_PATH + "/" + savedDto.getId())
                                .build().toUri())
                        .build());
    }

    @PutMapping(value = VET_PATH_ID)
    Mono<ResponseEntity<VetDTO>> updateVet(@Validated @RequestBody VetDTO dto, @PathVariable Integer id) {
        return vetService.updateVet(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedVet -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(value = VET_PATH_ID)
    Mono<ResponseEntity<Void>> deleteVet(@PathVariable Integer id) {
        return vetService.getVetById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(vetDto -> vetService.deleteVet(vetDto.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping(value = VET_PATH_ID)
    Mono<ResponseEntity<VetDTO>> patchVet(@Validated @RequestBody VetDTO dto, @PathVariable Integer id) {
        return vetService.patchVet(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedVet -> ResponseEntity.noContent().build());
    }

}