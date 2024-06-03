package com.example.petClinic.mapper;

import com.example.petClinic.domain.Pet;
import com.example.petClinic.dto.PetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntity(PetDTO dto);

    PetDTO toDTO(Pet pet);

}