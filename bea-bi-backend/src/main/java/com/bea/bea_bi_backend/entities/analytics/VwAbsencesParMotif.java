package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_ABSENCES_PAR_MOTIF")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwAbsencesParMotif {

    @Id
    @Column(name = "LIB_MOT")
    private String libMot;

    @Column(name = "TYP_CNG")
    private String typCng;

    @Column(name = "ABRV_MOT")
    private String abrvMot;

    // Ajout du champ Service pour le filtrage
    @Column(name = "LIB_SERV")
    private String libServ;

    // Ajout du champ Année pour le filtrage
    @Column(name = "ANNEE")
    private Integer annee;

    @Column(name = "NB_DEMANDES")
    private Long nbDemandes;

    @Column(name = "TOTAL_JOURS", precision = 7, scale = 3)
    private BigDecimal totalJours;
}