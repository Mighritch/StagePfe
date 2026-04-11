package com.bea.bea_bi_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PossedeVhId implements Serializable {

    @Column(name = "COD_SOC")
    private String codSoc;

    @Column(name = "MAT_PERS")
    private String matPers;

    @Column(name = "COD_TYP_BUL")
    private String codTypBul;

    @Column(name = "DT_BUL")
    private LocalDate dtBul;

    @Column(name = "ABRV_FIXE")
    private String abrvFixe;

    @Column(name = "SEQ", precision = 3)
    private Integer seq;
}