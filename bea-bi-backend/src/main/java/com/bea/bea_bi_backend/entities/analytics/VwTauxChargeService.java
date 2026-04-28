package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_TAUX_CHARGE_SERVICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwTauxChargeService {

    @Id
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "TAUX_MOYEN", precision = 5, scale = 2)
    private BigDecimal tauxMoyen;

    @Column(name = "TAUX_MAX", precision = 5, scale = 2)
    private BigDecimal tauxMax;

    @Column(name = "TAUX_MIN", precision = 5, scale = 2)
    private BigDecimal tauxMin;

    // Ajout de la colonne pour corriger l'erreur du Repository
    @Column(name = "ANNEE")
    private Integer annee;
}