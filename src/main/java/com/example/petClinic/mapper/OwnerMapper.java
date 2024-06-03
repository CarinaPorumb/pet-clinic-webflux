package com.example.petClinic.mapper;

import com.example.petClinic.domain.Owner;
import com.example.petClinic.dto.OwnerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    Owner toEntity(OwnerDTO dto);

    OwnerDTO toDTO(Owner owner);

}