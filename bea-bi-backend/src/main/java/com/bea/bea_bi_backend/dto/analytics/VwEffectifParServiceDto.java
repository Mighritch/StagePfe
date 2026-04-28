package com.bea.bea_bi_backend.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VwEffectifParServiceDto {
    private String libServ;
    private String typeServ;
    private Long nbEmployes;
    private String libGrad;
    private Integer annee;
}