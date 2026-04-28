package com.bea.bea_bi_backend.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VwEffectifParSexeDto {
    private String sexe;
    private Long nbEmployes;
    private Double pourcentage;
    private String libServ;
    private String libGrad;
    private Integer annee;
}