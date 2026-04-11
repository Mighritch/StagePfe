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
@Table(name = "DEM_CNG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemCng {

    @EmbeddedId
    private DemCngId id;

    @Column(name = "ANNEE_CNG")
    private Integer anneeCng;

    @Column(name = "CODE_M")
    private String codeM;

    @Column(name = "DAT_DCNG")
    private LocalDate datDcng;

    @Column(name = "DAT_DEBUT")
    private LocalDate datDebut;

    @Column(name = "DAT_FIN")
    private LocalDate datFin;

    @Column(name = "NBR_JOURS", precision = 7, scale = 3)
    private BigDecimal nbrJours;

    @Column(name = "NBR_JOURS_CAL", precision = 7, scale = 3)
    private BigDecimal nbrJoursCal;

    @Column(name = "MOTIF_CNG")
    private String motifCng;

    @Column(name = "VALID")
    private String valid;

    @Column(name = "ETAT_CNG")
    private String etatCng;

    @Column(name = "DAT_PREV_RET")
    private LocalDate datPrevRet;

    @Column(name = "DAT_RETOUR")
    private LocalDate datRetour;

    @Column(name = "NAT_CNG")
    private String natCng;

    @Column(name = "COD_SERV")
    private String codServ;

    @Column(name = "COD_MOTIF")
    private String codMotif;

    @Column(name = "SOLD_CNG", precision = 10, scale = 3)
    private BigDecimal soldCng;

    @Column(name = "NBR_JOURS_OUV", precision = 7, scale = 3)
    private BigDecimal nbrJoursOuv;

    @Column(name = "ID_DEM_CNG")
    private Long idDemCng;
}