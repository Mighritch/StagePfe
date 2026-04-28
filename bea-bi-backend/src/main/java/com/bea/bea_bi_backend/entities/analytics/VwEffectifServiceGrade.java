package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "VW_EFFECTIF_SERVICE_GRADE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEffectifServiceGrade {

    @Id
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "LIB_GRAD")
    private String libGrad;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    // Ajout de la colonne pour corriger l'erreur du Repository
    @Column(name = "ANNEE")
    private Integer annee;
}