package com.bea.bea_bi_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TYP_BULLETIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypBulletin {

    @Id
    @Column(name = "COD_TYP_BUL")
    private String codTypBul;

    @Column(name = "LIB_BULL")
    private String libBull;

    @Column(name = "TYP_BUL")
    private String typBul;

    @Column(name = "SOLDE")
    private String solde;

    @Column(name = "JOUR")
    private Integer jour;

    @Column(name = "DAT_DEB")
    private String datDeb;

    @Column(name = "DAT_FIN")
    private String datFin;

    @Column(name = "COEF_BUL", precision = 5, scale = 2)
    private BigDecimal coefBul;

    @Column(name = "LIB_BULL_A")
    private String libBullA;

    @Column(name = "CLOTURE")
    private String cloture;

    @Column(name = "RESERVE")
    private String reserve;

    @Column(name = "COD_TYP_BUL_REF")
    private String codTypBulRef;

    @Column(name = "NAT_CYCLE")
    private String natCycle;

    @Column(name = "REGIME_TRAV")
    private String regimeTrav;

    @Column(name = "COD_OPER")
    private String codOper;

    @Column(name = "CHAP_DEBIT")
    private String chapDebit;

    @Column(name = "CHAP_LIAIS")
    private String chapLiais;

    @Column(name = "CHAP_CRED")
    private String chapCred;

    @Column(name = "ORD")
    private Long ord;
}