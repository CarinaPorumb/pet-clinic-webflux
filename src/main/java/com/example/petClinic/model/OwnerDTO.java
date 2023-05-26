package com.example.petClinic.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank
    private String name;

    private String address;

    @NotBlank
    private String telephone;

}