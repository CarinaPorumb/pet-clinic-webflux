package com.example.petClinic.service;

import com.example.petClinic.dto.VetDTO;
import com.example.petClinic.mapper.VetMapper;
import com.example.petClinic.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    @Override
    public Flux<VetDTO> listVets() {
        return vetRepository.findAll().map(vetMapper::toDTO);
    }

    @Override
    public Mono<VetDTO> getVetById(Integer id) {
        return vetRepository.findById(id)
                .map(vetMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Vet not found")));
    }

    @Override
    public Mono<VetDTO> saveNewVet(VetDTO dto) {
        return vetRepository.save(vetMapper.toEntity(dto)).map(vetMapper::toDTO);
    }

    @Override
    public Mono<VetDTO> updateVet(VetDTO dto, Integer id) {
        return vetRepository.findById(id)
                .flatMap(foundVet -> {
                    foundVet.setName(dto.getName());
                    foundVet.setSpeciality(dto.getSpeciality());
                    return vetRepository.save(foundVet);
                })
                .map(vetMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Vet not found")));
    }

    @Override
    public Mono<Void> deleteVet(Integer id) {
        return vetRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Vet not found")))
                .flatMap(vetRepository::delete);
    }

    @Override
    public Mono<VetDTO> patchVet(VetDTO dto, Integer id) {
        return vetRepository.findById(id)
                .flatMap(foundVet -> {
                    if (StringUtils.hasText(dto.getName())) {
                        foundVet.setName(dto.getName());
                    }
                    if (StringUtils.hasText(dto.getSpeciality())) {
                        foundVet.setSpeciality(dto.getSpeciality());
                    }
                    return vetRepository.save(foundVet);
                })
                .map(vetMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Vet not found")));
    }

}