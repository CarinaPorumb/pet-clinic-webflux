package com.example.petClinic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Visit {

    @Id
    private Integer id;
    private String diagnosis;
    private Integer price;
    private LocalDate date;

}