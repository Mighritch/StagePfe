package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_ABSENCES_PAR_ANNEE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwAbsencesParAnnee {

    @Id
    @Column(name = "ANNEE_CNG")
    private Integer anneeCng;

    @Column(name = "TYP_CNG")
    private String typCng;

    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "NB_DEMANDES")
    private Long nbDemandes;

    @Column(name = "TOTAL_JOURS", precision = 7, scale = 3)
    private BigDecimal totalJours;
}