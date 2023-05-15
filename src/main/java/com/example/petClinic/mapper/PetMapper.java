package com.example.petClinic.mapper;

import com.example.petClinic.domain.Pet;
import com.example.petClinic.model.PetDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PetMapper {

    Pet petDtoToPet(PetDTO dto);

    PetDTO petToPetDto(Pet pet);

}