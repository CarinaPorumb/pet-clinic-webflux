package com.example.petClinic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("pets")
public class Pet {

    @Id
    private Integer id;
    private String name;
    private String petType;
    private Integer age;
    private Double weight;

    @CreatedDate
    private LocalDate birthdate;

}