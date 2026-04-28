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
public class VwAbsencesParAnneeDTO {
    private Integer anneeCng;
    private String typCng;
    private String libServ;
    private Long nbDemandes;
    private BigDecimal totalJours;
}