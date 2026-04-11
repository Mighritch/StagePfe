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
import java.time.LocalDate;

@Entity
@Table(name = "SERVICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    @EmbeddedId
    private ServiceId id;

    @Column(name = "SER_COD_SERV")
    private String serCodServ;

    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ABR_SERV")
    private String abrServ;

    @Column(name = "TYPE_SERV")
    private String typeServ;

    @Column(name = "LIB_SERV_A")
    private String libServA;

    @Column(name = "DEPT_ANAL")
    private String deptAnal;

    @Column(name = "ORDRE")
    private Integer ordre;

    @Column(name = "NB_SAL")
    private Integer nbSal;

    @Column(name = "COD_REG")
    private String codReg;

    @Column(name = "TAUX_DEF", precision = 7, scale = 3)
    private BigDecimal tauxDef;

    @Column(name = "MAT_PERS")
    private String matPers;

    @Column(name = "DAT_AFFECT")
    private LocalDate datAffect;

    @Column(name = "COD_LIEU_GEOG")
    private String codLieuGeog;

    @Column(name = "COD_ETAB")
    private String codEtab;
}