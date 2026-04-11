package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PretEnCoursDTO {
    private String matPers;
    private String codServ;
    private String codGrad;
    private String typPret;
    private BigDecimal prtMntGlb;     // Montant global accordé
    private BigDecimal prtMntRem;     // Montant remboursé
    private BigDecimal encours;       // prtMntGlb - prtMntRem
    private BigDecimal remMen;        // Remboursement mensuel
    private LocalDate prtDatAcc;      // Date accord
    private LocalDate prtDatFin;      // Date fin prévue
}