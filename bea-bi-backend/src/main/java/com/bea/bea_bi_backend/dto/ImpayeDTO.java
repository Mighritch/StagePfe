package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImpayeDTO {
    private String matPers;
    private Long nombreEcheancesImpayees;
    private BigDecimal montantImpaye;
}