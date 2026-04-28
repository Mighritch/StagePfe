package com.bea.bea_bi_backend.dto.analytics;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwEncoursParTypePretDTO {
    private String libPret;
    private String typPret;
    private String codGrpPret;
    private Long nbPrets;
    private BigDecimal encoursTotal;
    private BigDecimal montantMoyen;
    private String libServ;
    private Integer annee;
}