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
public class VwAbsencesParMotifDTO {
    private String libMot;
    private String typCng;
    private String abrvMot;
    private String libServ;
    private Integer annee;
    private Long nbDemandes;
    private BigDecimal totalJours;
}