package com.example.petClinic.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VetDTO {

    private Integer id;

    @NotBlank
    private String name;
    @NotBlank
    private String speciality;

}