package com.example.petClinic.controller;

import com.example.petClinic.model.OwnerDTO;
import com.example.petClinic.service.OwnerService;
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
public class OwnerController {

    public static final String OWNER_PATH = "/api/v2/owner";
    public static final String OWNER_PATH_ID = OWNER_PATH + "/{id}";

    private final OwnerService ownerService;

    @GetMapping(value = OWNER_PATH)
    Flux<OwnerDTO> listOwners() {
        return ownerService.listOwners();
    }

    @GetMapping(value = OWNER_PATH_ID)
    Mono<OwnerDTO> getOwnerById(@PathVariable("id") Integer id) {
        return ownerService.getOwnerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(value = OWNER_PATH)
    Mono<ResponseEntity<Void>> createNewOwner(@Validated @RequestBody OwnerDTO dto) {
        return ownerService.saveNewOwner(dto)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8082/" + OWNER_PATH + "/" + savedDto.getId())
                                .build().toUri())
                        .build());
    }

    @PutMapping(value = OWNER_PATH_ID)
    Mono<ResponseEntity<OwnerDTO>> updateOwner(@Validated @RequestBody OwnerDTO dto, @PathVariable Integer id) {
        return ownerService.updateOwner(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedOwner -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(value = OWNER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteOwner(@PathVariable Integer id) {
        return ownerService.getOwnerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(ownderDto -> ownerService.deleteOwner(ownderDto.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping(value = OWNER_PATH_ID)
    Mono<ResponseEntity<OwnerDTO>> patchOwner(@Validated @RequestBody OwnerDTO dto, @PathVariable Integer id) {
        return ownerService.patchOwner(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedOwner -> ResponseEntity.noContent().build());
    }

}