package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_SOLDE_CONGE_TYPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwSoldeCongeType {

    @Id
    @Column(name = "TYP_CNG")
    private String typCng;

    @Column(name = "SOLDE_MOYEN", precision = 7, scale = 3)
    private BigDecimal soldeMoyen;

    @Column(name = "INITIAL_MOYEN", precision = 7, scale = 3)
    private BigDecimal initialMoyen;

    @Column(name = "PRIS_MOYEN", precision = 7, scale = 3)
    private BigDecimal prisMoyen;

    @Column(name = "CUMUL_MOYEN", precision = 7, scale = 3)
    private BigDecimal cumulMoyen;

    // Ajout des colonnes pour le filtrage (Correction Repository)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}