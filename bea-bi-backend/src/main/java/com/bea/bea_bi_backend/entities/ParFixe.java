package com.bea.bea_bi_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PAR_FIXE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParFixe {

    @Id
    @Column(name = "ABRV_FIXE")
    private String abrvFixe;

    @Column(name = "LIB_FIXE")
    private String libFixe;

    @Column(name = "ABREVIATION")
    private String abreviation;

    @Column(name = "TYPE_PAR")
    private String typePar; // Type paramètre (salaire, prime, retenue...)

    @Column(name = "UNITE_FIXE")
    private String uniteFixe;

    @Column(name = "NATURE_FIXE")
    private String natureFixe;

    @Column(name = "SENS_IMPUT")
    private String sensImput; // C = crédit, D = débit

    @Column(name = "PLAFOND", precision = 10, scale = 3)
    private BigDecimal plafond;

    @Column(name = "TAUX_PERS", precision = 4, scale = 2)
    private BigDecimal tauxPers;

    @Column(name = "TAUX_PATR", precision = 4, scale = 2)
    private BigDecimal tauxPatr;

    @Column(name = "LIB_FIXE_A")
    private String libFixeA;

    @Column(name = "ACTIVE")
    private String active;

    @Column(name = "VALEUR", precision = 10, scale = 3)
    private BigDecimal valeur;

    @Column(name = "PLAFOND_ANNUEL", precision = 15, scale = 3)
    private BigDecimal plafondAnnuel;

    @Column(name = "R_RETRAITE")
    private String rRetraite;

    @Column(name = "IMPOT")
    private String impot;

    @Column(name = "R_JURID")
    private String rJurid;
}