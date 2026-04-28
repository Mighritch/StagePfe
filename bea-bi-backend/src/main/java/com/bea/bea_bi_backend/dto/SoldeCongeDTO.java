package com.bea.bea_bi_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoldeCongeDTO {

    @NotBlank
    private String matPers;

    @NotNull
    private Integer anneeCng;

    private String typCng;

    private BigDecimal initCng;   // Solde initial
    private BigDecimal cumCng;    // Cumulé
    private BigDecimal prisCng;   // Pris durant l'année
    private BigDecimal soldCng;   // Solde restant
}