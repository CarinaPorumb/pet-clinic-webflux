package com.example.petClinic.service;

import com.example.petClinic.dto.OwnerDTO;
import com.example.petClinic.mapper.OwnerMapper;
import com.example.petClinic.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Override
    public Flux<OwnerDTO> listOwners() {
        return ownerRepository.findAll().map(ownerMapper::toDTO);
    }

    @Override
    public Mono<OwnerDTO> getOwnerById(Integer id) {
        return ownerRepository.findById(id)
                .map(ownerMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Owner not found")));
    }

    @Override
    public Mono<OwnerDTO> saveNewOwner(OwnerDTO dto) {
        return ownerRepository.save(ownerMapper.toEntity(dto))
                .map(ownerMapper::toDTO);
    }

    @Override
    public Mono<OwnerDTO> updateOwner(OwnerDTO dto, Integer id) {
        return ownerRepository.findById(id)
                .flatMap(foundOwner -> {
                    foundOwner.setName(dto.getName());
                    foundOwner.setAddress(dto.getAddress());
                    foundOwner.setTelephone(dto.getTelephone());
                    return ownerRepository.save(foundOwner);
                })
                .map(ownerMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Owner not found")));
    }

    @Override
    public Mono<Void> deleteOwner(Integer id) {
        return ownerRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Owner not found")))
                .flatMap(ownerRepository::delete);

    }

    @Override
    public Mono<OwnerDTO> patchOwner(OwnerDTO dto, Integer id) {
        return ownerRepository.findById(id)
                .flatMap(foundOwner -> {
                    if (StringUtils.hasText(dto.getName())) {
                        foundOwner.setName(dto.getName());
                    }
                    if (StringUtils.hasText(dto.getAddress())) {
                        foundOwner.setAddress(dto.getAddress());
                    }
                    if (StringUtils.hasText(dto.getTelephone())) {
                        foundOwner.setTelephone(dto.getTelephone());
                    }
                    return ownerRepository.save(foundOwner);
                })
                .map(ownerMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Owner not found")));
    }

}