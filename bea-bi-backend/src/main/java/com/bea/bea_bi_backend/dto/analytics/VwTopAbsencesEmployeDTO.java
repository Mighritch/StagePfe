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
public class VwTopAbsencesEmployeDTO {
    private String matPers;
    private String nomPers;
    private String prenPers;
    private BigDecimal totalJoursAbsence;
    private Long nbDemandes;
    private String libServ;
    private Integer annee;
}