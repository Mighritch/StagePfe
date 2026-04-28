package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_SALAIRE_PAR_GRADE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwSalaireParGrade {

    @Id
    @Column(name = "LIB_GRAD")
    private String libGrad;

    @Column(name = "SALAIRE_MOYEN", precision = 12, scale = 2)
    private BigDecimal salaireMoyen;

    @Column(name = "SALAIRE_MIN", precision = 12, scale = 2)
    private BigDecimal salaireMin;

    @Column(name = "SALAIRE_MAX", precision = 12, scale = 2)
    private BigDecimal salaireMax;

    @Column(name = "TOTAL_CHARGES", precision = 12, scale = 2)
    private BigDecimal totalCharges;

    // Ajout des colonnes pour le filtrage (Correction Repository)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}