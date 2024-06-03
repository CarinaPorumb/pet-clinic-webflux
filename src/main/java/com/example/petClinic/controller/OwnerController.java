package com.example.petClinic.controller;

import com.example.petClinic.dto.OwnerDTO;
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
@RequestMapping("/api/v2/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public Flux<OwnerDTO> listOwners() {
        return ownerService.listOwners();
    }

    @GetMapping("/{id}")
    public Mono<OwnerDTO> getOwnerById(@PathVariable("id") Integer id) {
        return ownerService.getOwnerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found")));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createNewOwner(@Validated @RequestBody OwnerDTO dto) {
        return ownerService.saveNewOwner(dto)
                .map(savedDto -> ResponseEntity.created(
                                UriComponentsBuilder.fromPath("/api/v2/owner/{id}")
                                        .buildAndExpand(savedDto.getId())
                                        .toUri())
                        .build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<OwnerDTO>> updateOwner(@Validated @RequestBody OwnerDTO dto, @PathVariable("id") Integer id) {
        return ownerService.updateOwner(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found")))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteOwner(@PathVariable("id") Integer id) {
        return ownerService.getOwnerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found")))
                .flatMap(ownerDto -> ownerService.deleteOwner(ownerDto.getId()))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<OwnerDTO>> patchOwner(@Validated @RequestBody OwnerDTO dto, @PathVariable("id") Integer id) {
        return ownerService.patchOwner(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found")))
                .map(ResponseEntity::ok);
    }
}