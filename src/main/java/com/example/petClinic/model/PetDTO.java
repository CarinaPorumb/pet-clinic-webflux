package com.example.petClinic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetDTO {

    private Integer id;
    private String name;
    private String petType;
    private Integer age;
    private Double weight;
    private LocalDate birthdate;

}