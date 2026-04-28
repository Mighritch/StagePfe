package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_ENCOURS_PAR_TYPE_PRET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEncoursParTypePret {

    @Id
    @Column(name = "LIB_PRET")
    private String libPret;

    @Column(name = "TYP_PRET")
    private String typPret;

    @Column(name = "COD_GRP_PRET")
    private String codGrpPret;

    @Column(name = "NB_PRETS")
    private Long nbPrets;

    @Column(name = "ENCOURS_TOTAL", precision = 12, scale = 3)
    private BigDecimal encoursTotal;

    @Column(name = "MONTANT_MOYEN", precision = 12, scale = 3)
    private BigDecimal montantMoyen;

    // Ajout des colonnes pour le filtrage (Correction Repository)
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}