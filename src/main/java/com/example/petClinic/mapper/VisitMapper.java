package com.example.petClinic.mapper;

import com.example.petClinic.domain.Visit;
import com.example.petClinic.model.VisitDTO;
import org.mapstruct.Mapper;

@Mapper
public interface VisitMapper {

    Visit visitDtoToVisit(VisitDTO dto);

    VisitDTO visitToVisitDto(Visit visit);

}