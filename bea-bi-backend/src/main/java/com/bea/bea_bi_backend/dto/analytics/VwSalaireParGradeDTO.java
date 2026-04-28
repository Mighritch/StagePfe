package com.bea.bea_bi_backend.dto.analytics;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwSalaireParGradeDTO {
    private String libGrad;
    private BigDecimal salaireMoyen;
    private BigDecimal salaireMin;
    private BigDecimal salaireMax;
    private BigDecimal totalCharges;
    private String libServ;
    private Integer annee;
}