package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_ABSENTEISME_SERVICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwAbsenteismeService {

    @Id
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;

    @Column(name = "NB_EMPLOYES_ABSENTS")
    private Long nbEmployesAbsents;

    @Column(name = "TOTAL_JOURS_ABSENCE", precision = 7, scale = 3)
    private BigDecimal totalJoursAbsence;

    @Column(name = "MOYENNE_JOURS_PAR_EMPLOYE", precision = 7, scale = 2)
    private BigDecimal moyenneJoursParEmploye;
}