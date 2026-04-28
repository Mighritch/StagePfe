package com.bea.bea_bi_backend.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RubriqueDTO {

    private String abrvFixe;
    private String libFixe;
    private String typePar;
    private String sensImput;   // C = gain, D = retenue

    @PositiveOrZero
    private BigDecimal montantTotal;
}