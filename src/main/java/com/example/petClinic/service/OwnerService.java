package com.example.petClinic.service;

import com.example.petClinic.dto.OwnerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OwnerService {

    Flux<OwnerDTO> listOwners();

    Mono<OwnerDTO> getOwnerById(Integer id);

    Mono<OwnerDTO> saveNewOwner(OwnerDTO dto);

    Mono<OwnerDTO> updateOwner(OwnerDTO dto, Integer id);

    Mono<Void> deleteOwner(Integer id);

    Mono<OwnerDTO> patchOwner(OwnerDTO dto, Integer id);

}