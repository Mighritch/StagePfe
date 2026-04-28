package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_TOP_ABSENCES_EMPLOYE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwTopAbsencesEmploye {

    @Id
    @Column(name = "MAT_PERS")
    private String matPers;

    @Column(name = "NOM_PERS")
    private String nomPers;

    @Column(name = "PREN_PERS")
    private String prenPers;

    @Column(name = "TOTAL_JOURS_ABSENCE", precision = 7, scale = 3)
    private BigDecimal totalJoursAbsence;

    @Column(name = "NB_DEMANDES")
    private Long nbDemandes;

    // Ajout des colonnes pour le filtrage (Correction Repository)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}