package com.example.petClinic.controller;

import com.example.petClinic.dto.VetDTO;
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
@RequestMapping("/api/v2/vet")
public class VetController {

    private final VetService vetService;

    @GetMapping
    public Flux<VetDTO> listVets() {
        return vetService.listVets();
    }

    @GetMapping("/{id}")
    public Mono<VetDTO> getVetById(@PathVariable("id") Integer id) {
        return vetService.getVetById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Vet not found")));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createNewVet(@Validated @RequestBody VetDTO dto) {
        return vetService.saveNewVet(dto)
                .map(savedDto -> ResponseEntity.created(
                                UriComponentsBuilder.fromPath("/api/v2/vet/{id}")
                                        .buildAndExpand(savedDto.getId())
                                        .toUri())
                        .build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<VetDTO>> updateVet(@Validated @RequestBody VetDTO dto, @PathVariable("id") Integer id) {
        return vetService.updateVet(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Vet not found")))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteVet(@PathVariable("id") Integer id) {
        return vetService.getVetById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Vet not found")))
                .flatMap(vetDto -> vetService.deleteVet(vetDto.getId()))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<VetDTO>> patchVet(@Validated @RequestBody VetDTO dto, @PathVariable("id") Integer id) {
        return vetService.patchVet(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Vet not found")))
                .map(ResponseEntity::ok);
    }

}