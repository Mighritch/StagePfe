package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_TOP_EMPRUNTEURS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwTopEmprunteurs {

    @Id
    @Column(name = "MAT_PERS")
    private String matPers;

    @Column(name = "NOM_PERS")
    private String nomPers;

    @Column(name = "PREN_PERS")
    private String prenPers;

    @Column(name = "NB_PRETS")
    private Long nbPrets;

    @Column(name = "TOTAL_EMPRUNTE", precision = 12, scale = 3)
    private BigDecimal totalEmprunte;

    @Column(name = "TAUX_MOYEN", precision = 6, scale = 2)
    private BigDecimal tauxMoyen;

    // Ajout des colonnes de filtrage pour corriger le Repository
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}