package com.bea.bea_bi_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "AFFECTATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Affectation {

    @Id
    @Column(name = "COD_AFFECT")
    private String codAffect;

    @Column(name = "LIB_AFFECT")
    private String libAffect;

    @Column(name = "REGIME")
    private String regime;

    @Column(name = "JOURS", precision = 4, scale = 2)
    private BigDecimal jours;

    @Column(name = "HEURES", precision = 7, scale = 3)
    private BigDecimal heures;

    @Column(name = "RESERVE")
    private String reserve;

    @Column(name = "TYP_AFFECT")
    private String typAffect;

    @Column(name = "LIB_AFFECT_A")
    private String libAffectA;

    @Column(name = "NAT_AFFECT")
    private String natAffect;

    @Column(name = "COD_MVT")
    private String codMvt;
}