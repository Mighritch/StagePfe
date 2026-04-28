package com.bea.bea_bi_backend.dto.analytics;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwPretsParServiceDTO {
    private String libServ;
    private Long nbPrets;
    private BigDecimal totalCredit;
    private BigDecimal montantMoyen;
    private String typPret;
    private Integer annee;
}