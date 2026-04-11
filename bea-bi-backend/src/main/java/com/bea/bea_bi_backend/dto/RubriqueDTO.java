package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RubriqueDTO {
    private String abrvFixe;
    private String libFixe;
    private String typePar;
    private String sensImput;    // C = gain / D = retenue
    private BigDecimal montantTotal;
}