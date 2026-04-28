package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_TAUX_INTERET_PRET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwTauxInteretPret {

    @Id
    @Column(name = "LIB_PRET")
    private String libPret;

    @Column(name = "TAUX_MOYEN", precision = 6, scale = 2)
    private BigDecimal tauxMoyen;

    @Column(name = "TAUX_MIN", precision = 6, scale = 2)
    private BigDecimal tauxMin;

    @Column(name = "TAUX_MAX", precision = 6, scale = 2)
    private BigDecimal tauxMax;

    @Column(name = "TAUX_REFERENCE", precision = 7, scale = 3)
    private BigDecimal tauxReference;

    // Ajout des colonnes pour le filtrage (Correction Repository)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}