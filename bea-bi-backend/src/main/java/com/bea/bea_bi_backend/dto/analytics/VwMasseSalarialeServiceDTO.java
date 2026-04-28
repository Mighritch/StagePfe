package com.bea.bea_bi_backend.dto.analytics;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VwMasseSalarialeServiceDTO {
    private String libServ;
    private BigDecimal masseSalariale;
    private BigDecimal salaireMoyen;
    private Long nbEmployes;
    private Integer annee;
}