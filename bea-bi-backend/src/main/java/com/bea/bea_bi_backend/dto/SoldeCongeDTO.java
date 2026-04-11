package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoldeCongeDTO {
    private String matPers;
    private Integer anneeCng;
    private String typCng;
    private BigDecimal initCng;    // Solde initial
    private BigDecimal prisCng;    // Jours pris
    private BigDecimal soldCng;    // Solde restant
    private BigDecimal cumCng;     // Cumulé
}