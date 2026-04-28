package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_PRETS_PAR_DUREE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwPretsParDuree {

    @Id
    @Column(name = "DUREE_REMB")
    private Integer dureeRemb;

    @Column(name = "NB_PRETS")
    private Long nbPrets;

    @Column(name = "TOTAL_MONTANT", precision = 12, scale = 3)
    private BigDecimal totalMontant;

    @Column(name = "MOYENNE_MONTANT", precision = 12, scale = 3)
    private BigDecimal moyenneMontant;

    // Ajout des colonnes pour le filtrage (Résout l'erreur du Repository)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}