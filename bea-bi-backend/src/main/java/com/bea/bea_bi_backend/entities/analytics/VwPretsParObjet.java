package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_PRETS_PAR_OBJET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwPretsParObjet {

    @Id
    @Column(name = "OBJET_PRET")
    private String objetPret;

    @Column(name = "NB_PRETS")
    private Long nbPrets;

    @Column(name = "TOTAL_MONTANT", precision = 12, scale = 3)
    private BigDecimal totalMontant;

    @Column(name = "TAUX_MOYEN", precision = 6, scale = 2)
    private BigDecimal tauxMoyen;

    @Column(name = "DUREE_MOYENNE")
    private Double dureeMoyenne;

    // Ajout des colonnes de filtrage pour corriger le Repository
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}