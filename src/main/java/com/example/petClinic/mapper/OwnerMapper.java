package com.example.petClinic.mapper;

import com.example.petClinic.domain.Owner;
import com.example.petClinic.model.OwnerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OwnerMapper {

    Owner ownerDtoToOwner(OwnerDTO dto);

    OwnerDTO ownerToOwnerDto(Owner owner);

}