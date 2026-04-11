package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolutionPretDTO {
    private String mois;           // format YYYY-MM
    private Long nombrePrets;
    private BigDecimal montantAccorde;
}