package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_EVOLUTION_MASSE_SALARIALE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEvolutionMasseSalariale {

    @Id
    @Column(name = "ANNEE")
    private Integer annee;

    @Column(name = "MASSE_SALARIALE", precision = 12, scale = 2)
    private BigDecimal masseSalariale;

    @Column(name = "MASSE_PREC", precision = 12, scale = 2)
    private BigDecimal massePrec;

    @Column(name = "VARIATION_PCT", precision = 5, scale = 2)
    private BigDecimal variationPct;
}