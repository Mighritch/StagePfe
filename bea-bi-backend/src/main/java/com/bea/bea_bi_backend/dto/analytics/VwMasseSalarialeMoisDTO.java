package com.bea.bea_bi_backend.dto.analytics;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwMasseSalarialeMoisDTO {
    private Date mois;
    private BigDecimal masseSalariale;
    private Long nbEmployes;
    private String libServ;
    private Integer annee;
    private Integer moisNum;
}