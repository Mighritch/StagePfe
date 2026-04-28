package com.bea.bea_bi_backend.dto.analytics;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwPretsParObjetDTO {
    private String objetPret;
    private Long nbPrets;
    private BigDecimal totalMontant;
    private BigDecimal tauxMoyen;
    private Double dureeMoyenne;
    private String libServ;
    private Integer annee;
}