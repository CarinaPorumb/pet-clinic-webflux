package com.example.petClinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VisitDTO {

    private Integer id;

    @NotBlank
    private String diagnosis;
    private Integer price;
    private LocalDate date;

}