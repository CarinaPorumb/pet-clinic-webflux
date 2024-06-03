package com.example.petClinic.service;

import com.example.petClinic.dto.PetDTO;
import com.example.petClinic.mapper.PetMapper;
import com.example.petClinic.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    @Override
    public Flux<PetDTO> listPets() {
        return petRepository.findAll().map(petMapper::toDTO);
    }

    @Override
    public Mono<PetDTO> getPetById(Integer id) {
        return petRepository.findById(id)
                .map(petMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Pet not found")));
    }

    @Override
    public Mono<PetDTO> saveNewPet(PetDTO dto) {
        return petRepository.save(petMapper.toEntity(dto))
                .map(petMapper::toDTO);
    }

    @Override
    public Mono<PetDTO> updatePet(PetDTO dto, Integer id) {
        return petRepository.findById(id)
                .flatMap(foundPet -> {
                    foundPet.setName(dto.getName());
                    foundPet.setPetType(dto.getPetType());
                    foundPet.setAge(dto.getAge());
                    foundPet.setWeight(dto.getWeight());
                    foundPet.setBirthdate(dto.getBirthdate());
                    return petRepository.save(foundPet);
                })
                .map(petMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Pet not found")));
    }

    @Override
    public Mono<Void> deletePet(Integer id) {
        return petRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Pet not found")))
                .flatMap(petRepository::delete);
    }

    @Override
    public Mono<PetDTO> patchPet(PetDTO dto, Integer id) {
        return petRepository.findById(id)
                .flatMap(foundPet -> {
                    if (StringUtils.hasText(dto.getName())) {
                        foundPet.setName(dto.getName());
                    }
                    if (StringUtils.hasText(dto.getPetType())) {
                        foundPet.setPetType(dto.getPetType());
                    }
                    if (dto.getAge() != null) {
                        foundPet.setAge(dto.getAge());
                    }
                    if (dto.getWeight() != null) {
                        foundPet.setWeight(dto.getWeight());
                    }
                    if (dto.getBirthdate() != null) {
                        foundPet.setBirthdate(dto.getBirthdate());
                    }
                    return petRepository.save(foundPet);
                })
                .map(petMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Pet not found")));
    }
}