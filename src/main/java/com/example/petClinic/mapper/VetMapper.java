package com.example.petClinic.mapper;

import com.example.petClinic.domain.Vet;
import com.example.petClinic.dto.VetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetMapper {

    Vet toEntity(VetDTO dto);

    VetDTO toDTO(Vet vet);

}