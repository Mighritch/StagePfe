package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsCreditDTO {

    private String code;                    // codServ, codGrad ou typPret selon contexte
    private Long nombrePrets;
    private BigDecimal montantTotal;
    private BigDecimal montantRembourse;
}