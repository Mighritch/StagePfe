package com.bea.bea_bi_backend.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VwEffectifEvolutionDto {
    private Date mois;
    private Long nbEmployes;
    private String libServ;
    private String libGrad;
    private Integer annee;
}
