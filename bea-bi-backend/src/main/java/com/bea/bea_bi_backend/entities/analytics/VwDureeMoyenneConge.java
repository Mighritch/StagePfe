package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_DUREE_MOYENNE_CONGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwDureeMoyenneConge {

    @Id
    @Column(name = "LIB_MOT")
    private String libMot;

    @Column(name = "DUREE_MOYENNE", precision = 7, scale = 2)
    private BigDecimal dureeMoyenne;

    @Column(name = "DUREE_MIN", precision = 7, scale = 3)
    private BigDecimal dureeMin;

    @Column(name = "DUREE_MAX", precision = 7, scale = 3)
    private BigDecimal dureeMax;

    // Ajout des colonnes pour résoudre l'erreur du Repository
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}