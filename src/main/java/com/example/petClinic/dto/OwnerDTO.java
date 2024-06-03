package com.example.petClinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OwnerDTO {

    private Integer id;

    @NotBlank
    private String name;

    private String address;

    @NotBlank
    private String telephone;

}