package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "VW_MASSE_SALARIALE_MOIS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwMasseSalarialeMois {

    @Id
    @Column(name = "MOIS")
    private Date mois;

    @Column(name = "MASSE_SALARIALE", precision = 12, scale = 2)
    private BigDecimal masseSalariale;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    // Ajout des colonnes pour le filtrage (Correction Repository)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;

    @Column(name = "MOIS_NUM") // Correspond au numéro du mois (1-12)
    private Integer moisNum;
}