package com.bea.bea_bi_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TYP_ADM_TECH")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypAdmTech {

    @Id
    @Column(name = "ADM_TECH")
    private String admTech;

    @Column(name = "LIB_ADM_TECH")
    private String libAdmTech;

    @Column(name = "ABRV_ADM_TECH")
    private String abrvAdmTech;

    @Column(name = "LIB_ADM_TECH_A")
    private String libAdmTechA;

    @Column(name = "TYP_ADM_TEC")
    private String typAdmTec;

    @Column(name = "COD_GRAD")
    private String codGrad;

    @Column(name = "H_DEROG", precision = 5, scale = 2)
    private BigDecimal hDerog;

    @Column(name = "COD_CAT")
    private String codCat;

    @Column(name = "COD_CATEG")
    private String codCateg;

    @Column(name = "COD_FIL")
    private String codFil;

    @Column(name = "POST_ORGANIQUE")
    private String postOrganique;

    @Column(name = "COD_POST")
    private String codPost;

    @Column(name = "NIVEAU")
    private String niveau;

    @Column(name = "DROIT_VEHICUL")
    private String droitVehicul;
}