package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandicapeDTO {
    private String matPers;
    private String nomPers;
    private String prenPers;
    private String codServ;
    private BigDecimal pourcentHand;
}