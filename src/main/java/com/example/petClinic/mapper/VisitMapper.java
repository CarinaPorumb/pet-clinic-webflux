package com.example.petClinic.mapper;

import com.example.petClinic.domain.Visit;
import com.example.petClinic.dto.VisitDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    Visit toEntity(VisitDTO dto);

    VisitDTO toDTO(Visit visit);

}