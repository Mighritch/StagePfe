package com.bea.bea_bi_backend.dto.analytics;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwPretsParDureeDTO {
    private Integer dureeRemb;
    private Long nbPrets;
    private BigDecimal totalMontant;
    private BigDecimal moyenneMontant;
    private String libServ;
    private Integer annee;
}