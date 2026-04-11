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
@Table(name = "TYPE_PRET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypePret {

    @EmbeddedId
    private TypePretId id;

    @Column(name = "ABRV_FIXE")
    private String abrvFixe;

    @Column(name = "LIB_PRET")
    private String libPret;

    @Column(name = "LIB_PRET_A")
    private String libPretA;

    @Column(name = "TAUX_INT", precision = 7, scale = 3)
    private BigDecimal tauxInt;

    @Column(name = "DUREE_REMB")
    private Integer dureeRemb;

    @Column(name = "DELAI_GRACE")
    private Integer delaiGrace;

    @Column(name = "ANCIENNETE")
    private Integer anciennete;

    @Column(name = "PLAFOND", precision = 10, scale = 3)
    private BigDecimal plafond;

    @Column(name = "ASS_PRET")
    private String assPret;

    @Column(name = "RENOUV")
    private String renouv;

    @Column(name = "PRT_LOG")
    private String prtLog;

    @Column(name = "ID_TYPE_PRET")
    private Long idTypePret;
}