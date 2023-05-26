package com.example.petClinic.service;

import com.example.petClinic.mapper.VisitMapper;
import com.example.petClinic.model.VisitDTO;
import com.example.petClinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    @Override
    public Flux<VisitDTO> listVisits() {
        return visitRepository.findAll().map(visitMapper::visitToVisitDto);
    }

    @Override
    public Mono<VisitDTO> getVisitById(Integer id) {
        return visitRepository.findById(id).map(visitMapper::visitToVisitDto);
    }

    @Override
    public Mono<VisitDTO> saveNewVisit(VisitDTO dto) {
        return visitRepository.save(visitMapper.visitDtoToVisit(dto)).map(visitMapper::visitToVisitDto);
    }

    @Override
    public Mono<VisitDTO> updateVisit(VisitDTO dto, Integer id) {
        return visitRepository.findById(id)
                .map(foundVisit -> {
                    foundVisit.setDiagnosis(dto.getDiagnosis());
                    foundVisit.setPrice(dto.getPrice());
                    foundVisit.setDate(dto.getDate());
                    return foundVisit;
                })
                .flatMap(visitRepository::save)
                .map(visitMapper::visitToVisitDto);
    }

    @Override
    public Mono<Void> deleteVisit(Integer id) {
        return visitRepository.deleteById(id);
    }

    @Override
    public Mono<VisitDTO> patchVisit(VisitDTO dto, Integer id) {
        return visitRepository.findById(id)
                .map(foundVisit -> {
                    if (StringUtils.hasText(dto.getDiagnosis())) {
                        foundVisit.setDiagnosis(dto.getDiagnosis());
                    }
                    if (dto.getPrice() != null) {
                        foundVisit.setPrice(dto.getPrice());
                    }
                    if (dto.getDate() != null) {
                        foundVisit.setDate(dto.getDate());
                    }
                    return foundVisit;
                })
                .flatMap(visitRepository::save)
                .map(visitMapper::visitToVisitDto);
    }

}