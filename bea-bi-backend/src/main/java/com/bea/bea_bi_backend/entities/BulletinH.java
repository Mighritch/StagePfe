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
@Table(name = "BULLETINH")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulletinH {

    @EmbeddedId
    private BulletinHId id;

    @Column(name = "DATEV")
    private LocalDate datev;

    @Column(name = "BUL_COD_TYP_BUL")
    private String bulCodTypBul;

    @Column(name = "COD_LIEU_GEOG")
    private String codLieuGeog;

    @Column(name = "COD_DEPT")
    private String codDept;

    @Column(name = "COD_SERV")
    private String codServ;

    @Column(name = "COD_SIT")
    private String codSit;

    @Column(name = "NBR_ENF")
    private Integer nbrEnf;

    @Column(name = "CHEF_FAM")
    private String chefFam;

    @Column(name = "ETAT_ACT")
    private String etatAct;

    @Column(name = "COD_MOTIF")
    private String codMotif;

    @Column(name = "COD_STAT")
    private String codStat;

    @Column(name = "COD_AFFECT")
    private String codAffect;

    @Column(name = "COD_CATEG")
    private String codCateg;

    @Column(name = "COD_CAT")
    private String codCat;

    @Column(name = "COD_GRAD")
    private String codGrad;

    @Column(name = "COD_ECH")
    private Integer codEch;

    @Column(name = "COD_FONCT")
    private String codFonct;

    @Column(name = "ADM_TECH")
    private String admTech;

    @Column(name = "PRESENCE", precision = 7, scale = 3)
    private BigDecimal presence;

    @Column(name = "DAT_MODIF")
    private LocalDate datModif;

    @Column(name = "COD_USER")
    private String codUser;

    @Column(name = "NIV_SAL")
    private Integer nivSal;

    @Column(name = "POSTE_TRAV")
    private String posteTrav;

    @Column(name = "ORG_SERV")
    private String orgServ;

    @Column(name = "ID_BULLETINH")
    private Long idBulletinh;
}