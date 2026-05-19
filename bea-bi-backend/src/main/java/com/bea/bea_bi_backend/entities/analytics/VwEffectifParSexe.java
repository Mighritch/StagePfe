package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

/**
 * Entité mappée sur la vue SQL VW_EFFECTIF_PAR_SEXE.
 * Utilise Hibernate @Immutable car une vue est généralement en lecture seule.
 */
@Entity
@Table(name = "VW_EFFECTIF_PAR_SEXE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEffectifParSexe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROW_ID")
    private Long rowId; // Assurez-vous que votre vue SQL contient : ROW_NUMBER() OVER (ORDER BY ...) AS ROW_ID

    @Column(name = "SEXE")
    private String sexe;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    @Column(name = "POURCENTAGE")
    private Double pourcentage;

    // Colonnes utilisées pour le filtrage dans le Repository
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "LIB_GRAD")
    private String libGrad;

    @Column(name = "ANNEE")
    private Integer annee;
}