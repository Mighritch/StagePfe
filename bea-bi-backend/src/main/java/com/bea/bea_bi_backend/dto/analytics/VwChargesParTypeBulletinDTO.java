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
public class VwChargesParTypeBulletinDTO {
    private String codTypBul;
    private String abrvFixe;
    private BigDecimal totalCharges;
    private BigDecimal moyenneCharge;
    private Long nbBulletins;
    private String libServ;
    private Integer annee;
}