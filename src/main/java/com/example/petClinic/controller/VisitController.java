package com.example.petClinic.controller;

import com.example.petClinic.dto.VisitDTO;
import com.example.petClinic.service.VisitService;
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
@RequestMapping("/api/v2/visit")
public class VisitController {

    private final VisitService visitService;

    @GetMapping
    public Flux<VisitDTO> listVisits() {
        return visitService.listVisits();
    }

    @GetMapping("/{id}")
    public Mono<VisitDTO> getVisitById(@PathVariable("id") Integer id) {
        return visitService.getVisitById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found")));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createNewVisit(@Validated @RequestBody VisitDTO dto) {
        return visitService.saveNewVisit(dto)
                .map(savedDto -> ResponseEntity.created(
                                UriComponentsBuilder.fromPath("/api/v2/visit/{id}")
                                        .buildAndExpand(savedDto.getId())
                                        .toUri())
                        .build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<VisitDTO>> updateVisit(@Validated @RequestBody VisitDTO dto, @PathVariable("id") Integer id) {
        return visitService.updateVisit(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found")))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteVisit(@PathVariable("id") Integer id) {
        return visitService.getVisitById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found")))
                .flatMap(visitDto -> visitService.deleteVisit(visitDto.getId()))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<VisitDTO>> patchVisit(@Validated @RequestBody VisitDTO dto, @PathVariable("id") Integer id) {
        return visitService.patchVisit(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found")))
                .map(ResponseEntity::ok);
    }

}