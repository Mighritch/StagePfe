package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcheancierDTO {
    private Long lPret;            // Numéro de ligne
    private LocalDate moisPretPrevu;
    private LocalDate moisPret;
    private BigDecimal mntPeriod;  // Montant période
    private BigDecimal mntInt;     // Montant intérêt
    private BigDecimal capRest;    // Capital restant
    private String impaye;         // O/N
    private String valPret;        // O/N
}