package com.bea.bea_bi_backend.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VwEffectifServiceGradeDto {
    private String libServ;
    private String libGrad;
    private Long nbEmployes;
    private Integer annee;
}