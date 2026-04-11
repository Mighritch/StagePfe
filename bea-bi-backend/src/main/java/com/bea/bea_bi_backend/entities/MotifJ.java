package com.bea.bea_bi_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MOTIF_J")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MotifJ {

    @Id
    @Column(name = "COD_M")
    private String codM;

    @Column(name = "TYP_CNG")
    private String typCng;

    @Column(name = "ABRV_FIXE")
    private String abrvFixe;

    @Column(name = "LIB_MOT")
    private String libMot;

    @Column(name = "ABRV_MOT")
    private String abrvMot;

    @Column(name = "DED_SAL")
    private String dedSal;

    @Column(name = "DED_CNG")
    private String dedCng;

    @Column(name = "PLAFOND_CNG")
    private Integer plafondCng;

    @Column(name = "UNITE")
    private String unite;

    @Column(name = "LIB_MOT_A")
    private String libMotA;

    @Column(name = "NATURE_CNG")
    private String natureCng;

    @Column(name = "TYPE_ABS")
    private String typeAbs;

    @Column(name = "NAT_CNG")
    private String natCng;
}