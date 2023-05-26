package com.example.petClinic.service;

import com.example.petClinic.mapper.OwnerMapper;
import com.example.petClinic.model.OwnerDTO;
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
        return ownerRepository.findAll().map(ownerMapper::ownerToOwnerDto);
    }

    @Override
    public Mono<OwnerDTO> getOwnerById(Integer id) {
        return ownerRepository.findById(id).map(ownerMapper::ownerToOwnerDto);
    }

    @Override
    public Mono<OwnerDTO> saveNewOwner(OwnerDTO dto) {
        return ownerRepository.save(ownerMapper.ownerDtoToOwner(dto))
                .map(ownerMapper::ownerToOwnerDto);
    }

    @Override
    public Mono<OwnerDTO> updateOwner(OwnerDTO dto, Integer id) {
        return ownerRepository.findById(id)
                .map(foundOwner -> {
                    foundOwner.setName(dto.getName());
                    foundOwner.setAddress(dto.getAddress());
                    foundOwner.setTelephone(dto.getTelephone());
                    return foundOwner;
                })
                .flatMap(ownerRepository::save)
                .map(ownerMapper::ownerToOwnerDto);
    }

    @Override
    public Mono<Void> deleteOwner(Integer id) {
        return ownerRepository.deleteById(id);
    }

    @Override
    public Mono<OwnerDTO> patchOwner(OwnerDTO dto, Integer id) {
        return ownerRepository.findById(id)
                .map(foundOwner -> {
                    if (StringUtils.hasText(dto.getName())) {
                        foundOwner.setName(dto.getName());
                    }
                    if (StringUtils.hasText(dto.getAddress())) {
                        foundOwner.setAddress(dto.getAddress());
                    }
                    if (StringUtils.hasText(dto.getTelephone())) {
                        foundOwner.setTelephone(dto.getTelephone());
                    }
                    return foundOwner;
                })
                .flatMap(ownerRepository::save)
                .map(ownerMapper::ownerToOwnerDto);
    }

}