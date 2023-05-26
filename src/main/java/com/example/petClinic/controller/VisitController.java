package com.example.petClinic.controller;

import com.example.petClinic.model.VisitDTO;
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
public class VisitController {

    public static final String VISIT_PATH = "/api/v2/visit";
    public static final String VISIT_PATH_ID = VISIT_PATH + "/{id}";

    private final VisitService visitService;

    @GetMapping(value = VISIT_PATH)
    Flux<VisitDTO> listVisits() {
        return visitService.listVisits();
    }

    @GetMapping(value = VISIT_PATH_ID)
    Mono<VisitDTO> getVisitById(@PathVariable Integer id) {
        return visitService.getVisitById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(value = VISIT_PATH)
    Mono<ResponseEntity<VisitDTO>> createNewVisit(@Validated @RequestBody VisitDTO dto) {
        return visitService.saveNewVisit(dto)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8082/" + VISIT_PATH + "/" + savedDto.getId())
                                .build().toUri())
                        .build());
    }

    @PutMapping(value = VISIT_PATH_ID)
    Mono<ResponseEntity<VisitDTO>> updateVisit(@Validated @RequestBody VisitDTO dto, @PathVariable Integer id) {
        return visitService.updateVisit(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedVisit -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(value = VISIT_PATH_ID)
    Mono<ResponseEntity<Void>> deleteVisit(@PathVariable Integer id) {
        return visitService.getVisitById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(visitDto -> visitService.deleteVisit(visitDto.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping(value = VISIT_PATH_ID)
    Mono<ResponseEntity<VisitDTO>> patchVisit(@Validated @RequestBody VisitDTO dto, @PathVariable Integer id) {
        return visitService.patchVisit(dto, id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedVisit -> ResponseEntity.noContent().build());
    }

}