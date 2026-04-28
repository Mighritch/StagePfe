package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "VW_EFFECTIF_EVOLUTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEffectifEvolution {

    @Id
    @Column(name = "MOIS")
    private Date mois;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    // Ajouts pour le filtrage (image_85f8a0.png)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "LIB_GRAD")
    private String libGrad;

    @Column(name = "ANNEE")
    private Integer annee;
}