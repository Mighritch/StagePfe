package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "VW_EFFECTIF_PAR_SERVICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEffectifParService {

    @Id
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "TYPE_SERV")
    private String typeServ;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    // Ajout des colonnes pour corriger les erreurs du Repository
    @Column(name = "LIB_GRAD")
    private String libGrad;

    @Column(name = "ANNEE")
    private Integer annee;
}