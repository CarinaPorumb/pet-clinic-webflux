package com.example.petClinic.mapper;

import com.example.petClinic.domain.Vet;
import com.example.petClinic.model.VetDTO;
import org.mapstruct.Mapper;

@Mapper
public interface VetMapper {

    Vet vetDtoToVet(VetDTO dto);

    VetDTO vetToVetDto(Vet vet);

}