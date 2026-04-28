package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "VW_EFFECTIF_PAR_GRADE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEffectifParGrade {

    @Id
    @Column(name = "LIB_GRAD")
    private String libGrad;

    @Column(name = "COD_CATEG")
    private String codCateg;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    // Ajout des colonnes pour le filtrage (Correction Repository)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}