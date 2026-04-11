package com.bea.bea_bi_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PERSONNEL_HIST")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonnelHist {

    @Id
    @Column(name = "MAT_PERS")
    private String matPers;

    @Column(name = "COD_SOC")
    private String codSoc;

    @Column(name = "NOM_PERS")
    private String nomPers;

    @Column(name = "PREN_PERS")
    private String prenPers;

    @Column(name = "NOM_PERS_A")
    private String nomPersA;

    @Column(name = "PREN_PERS_A")
    private String prenPersA;

    @Column(name = "CIN")
    private String cin;

    @Column(name = "SEXE")
    private String sexe;

    @Column(name = "COD_SIT")
    private String codSit; // Situation familiale

    @Column(name = "DAT_SIT")
    private LocalDate datSit;

    @Column(name = "CHEF_FAM")
    private String chefFam;

    @Column(name = "NBRE_ENF")
    private Integer nbreEnf;

    @Column(name = "DAT_ENT")
    private LocalDate datEnt; // Date entrée

    @Column(name = "DAT_TIT")
    private LocalDate datTit; // Date titularisation

    @Column(name = "COD_SERV")
    private String codServ;

    @Column(name = "COD_FONCT")
    private String codFonct;

    @Column(name = "COD_CATEG")
    private String codCateg;

    @Column(name = "COD_CAT")
    private String codCat;

    @Column(name = "COD_GRAD")
    private String codGrad;

    @Column(name = "COD_MOTIF")
    private String codMotif;

    @Column(name = "COD_STAT")
    private String codStat;

    @Column(name = "DAT_SERV")
    private LocalDate datServ;

    @Column(name = "DAT_GRAD")
    private LocalDate datGrad;

    @Column(name = "DAT_NAIS")
    private LocalDate datNais;

    @Column(name = "ETAT_ACT")
    private String etatAct; // Actif / Inactif

    @Column(name = "ADM_TECH")
    private String admTech;

    @Column(name = "COD_ECH")
    private Integer codEch;

    @Column(name = "COD_AFFECT")
    private String codAffect;

    @Column(name = "POSTE_TRAV")
    private String posteTrav;

    @Column(name = "DAT_EMB")
    private LocalDate datEmb;

    @Column(name = "COD_DIR")
    private String codDir;

    @Column(name = "COD_UF")
    private String codUf;

    @Column(name = "DAT_DEPART")
    private LocalDate datDepart;

    @Column(name = "COD_TYP_DEPART")
    private String codTypDepart;

    @Column(name = "LIEU_NAIS")
    private String lieuNais;

    @Column(name = "HANDICAP")
    private String handicap;

    @Column(name = "POURCENT_HAND")
    private BigDecimal pourcentHand;

    @Column(name = "NIV_SAL")
    private Integer nivSal;

    @Column(name = "DATE_HIST")
    private LocalDate dateHist;

    @Column(name = "ID_PERSONNEL")
    private Long idPersonnel;
}