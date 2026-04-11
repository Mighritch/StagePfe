package com.bea.bea_bi_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "GRADE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {

    @EmbeddedId
    private GradeId id;

    @Column(name = "LIB_GRAD")
    private String libGrad;

    @Column(name = "ABRV_GRAD1")
    private String abrvGrad1;

    @Column(name = "ABRV_GRAD2")
    private String abrvGrad2;

    @Column(name = "DUREE")
    private Integer duree;

    @Column(name = "NBR_ECH")
    private Integer nbrEch;

    @Column(name = "LIB_GRAD_A")
    private String libGradA;

    @Column(name = "MNT_COMP", precision = 15, scale = 3)
    private BigDecimal mntComp;

    @Column(name = "COD_SOC")
    private String codSoc;

    @Column(name = "TYP_CAT")
    private String typCat;

    @Column(name = "ID_GRADE")
    private Long idGrade;
}