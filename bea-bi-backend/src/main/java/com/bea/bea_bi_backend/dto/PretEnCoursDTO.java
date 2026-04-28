package com.bea.bea_bi_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PretEnCoursDTO {

    @NotBlank
    private String matPers;

    private String codServ;
    private String codGrad;
    private String typPret;

    private BigDecimal prtMntGlb;
    private BigDecimal prtMntRem;

    private BigDecimal encours;

    private BigDecimal remMen;
    private LocalDate prtDatAcc;
    private LocalDate prtDatFin;

    public BigDecimal getEncours() {
        if (encours != null) {
            return encours;
        }
        return (prtMntGlb != null ? prtMntGlb : BigDecimal.ZERO)
                .subtract(prtMntRem != null ? prtMntRem : BigDecimal.ZERO);
    }
}