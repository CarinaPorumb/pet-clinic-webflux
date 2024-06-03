package com.example.petClinic.service;

import com.example.petClinic.dto.VisitDTO;
import com.example.petClinic.mapper.VisitMapper;
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
        return visitRepository.findAll().map(visitMapper::toDTO);
    }

    @Override
    public Mono<VisitDTO> getVisitById(Integer id) {
        return visitRepository.findById(id)
                .map(visitMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("No visit found")));
    }

    @Override
    public Mono<VisitDTO> saveNewVisit(VisitDTO dto) {
        return visitRepository.save(visitMapper.toEntity(dto)).map(visitMapper::toDTO);
    }

    @Override
    public Mono<VisitDTO> updateVisit(VisitDTO dto, Integer id) {
        return visitRepository.findById(id)
                .flatMap(foundVisit -> {
                    foundVisit.setDiagnosis(dto.getDiagnosis());
                    foundVisit.setPrice(dto.getPrice());
                    foundVisit.setDate(dto.getDate());
                    return visitRepository.save(foundVisit);
                })
                .map(visitMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("No visit found")));
    }

    @Override
    public Mono<Void> deleteVisit(Integer id) {
        return visitRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("No visit found")))
                .flatMap(visitRepository::delete);
    }

    @Override
    public Mono<VisitDTO> patchVisit(VisitDTO dto, Integer id) {
        return visitRepository.findById(id)
                .flatMap(foundVisit -> {
                    if (StringUtils.hasText(dto.getDiagnosis())) {
                        foundVisit.setDiagnosis(dto.getDiagnosis());
                    }
                    if (dto.getPrice() != null) {
                        foundVisit.setPrice(dto.getPrice());
                    }
                    if (dto.getDate() != null) {
                        foundVisit.setDate(dto.getDate());
                    }
                    return visitRepository.save(foundVisit);
                })
                .map(visitMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("No visit found")));
    }
}