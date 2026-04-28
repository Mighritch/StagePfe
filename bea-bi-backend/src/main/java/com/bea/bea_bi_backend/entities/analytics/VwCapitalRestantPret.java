package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_CAPITAL_RESTANT_PRET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwCapitalRestantPret {

    @Id
    @Column(name = "LIB_PRET")
    private String libPret;

    @Column(name = "CAPITAL_RESTANT", precision = 12, scale = 3)
    private BigDecimal capitalRestant;

    @Column(name = "TOTAL_ECHEANCES", precision = 12, scale = 3)
    private BigDecimal totalEcheances;

    @Column(name = "TOTAL_INTERETS", precision = 12, scale = 3)
    private BigDecimal totalInterets;

    // Ajout du champ manquant pour résoudre l'erreur dans le repository
    @Column(name = "ANNEE")
    private Integer annee;
}