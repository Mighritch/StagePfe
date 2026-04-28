package com.bea.bea_bi_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EffectifParAdmTechDTO {

    @NotBlank(message = "Le code ADM_TECH est obligatoire")
    private String admTech;

    @NotNull
    private Long nombre;
}