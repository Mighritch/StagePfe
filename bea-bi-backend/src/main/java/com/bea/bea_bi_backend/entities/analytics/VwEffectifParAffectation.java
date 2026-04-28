package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "VW_EFFECTIF_PAR_AFFECTATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEffectifParAffectation {

    @Id
    @Column(name = "LIB_AFFECT")
    private String libAffect;

    @Column(name = "REGIME")
    private String regime;

    @Column(name = "JOURS")
    private Double jours;

    @Column(name = "HEURES")
    private Double heures;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    // Ajout des colonnes pour corriger les erreurs du Repository
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}