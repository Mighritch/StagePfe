package com.bea.bea_bi_backend.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwTauxInteretPretDTO {
    private String libPret;
    private BigDecimal tauxMoyen;
    private BigDecimal tauxMin;
    private BigDecimal tauxMax;
    private BigDecimal tauxReference;
    private String libServ;
    private Integer annee;
}