package com.example.petClinic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vet {

    @Id
    private Integer id;
    private String name;
    private String speciality;

}