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
public class VwDureeMoyenneCongeDTO {
    private String libMot;
    private BigDecimal dureeMoyenne;
    private BigDecimal dureeMin;
    private BigDecimal dureeMax;
    private String libServ;
    private Integer annee;
}