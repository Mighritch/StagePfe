package com.bea.bea_bi_backend.dto.analytics;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwTauxChargeServiceDTO {
    private String libServ;
    private BigDecimal tauxMoyen;
    private BigDecimal tauxMax;
    private BigDecimal tauxMin;
    private Integer annee;
}