package com.example.petClinic.service;

import com.example.petClinic.mapper.PetMapper;
import com.example.petClinic.model.PetDTO;
import com.example.petClinic.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    @Override
    public Flux<PetDTO> listPets() {
        return petRepository.findAll()
                .map(petMapper::petToPetDto);
    }

    @Override
    public Mono<PetDTO> getById(Integer id) {
        return petRepository.findById(id)
                .map(petMapper::petToPetDto);
    }

    @Override
    public Mono<PetDTO> saveNewPet(PetDTO dto) {
        return petRepository.save(petMapper.petDtoToPet(dto))
                .map(petMapper::petToPetDto);
    }

}