package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_CHARGES_PAR_TYPE_BULLETIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwChargesParTypeBulletin {

    @Id
    @Column(name = "COD_TYP_BUL")
    private String codTypBul;

    @Column(name = "ABRV_FIXE")
    private String abrvFixe;

    @Column(name = "TOTAL_CHARGES", precision = 12, scale = 2)
    private BigDecimal totalCharges;

    @Column(name = "MOYENNE_CHARGE", precision = 12, scale = 2)
    private BigDecimal moyenneCharge;

    @Column(name = "NB_BULLETINS")
    private Long nbBulletins;

    // Ajout des colonnes pour le filtrage
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}