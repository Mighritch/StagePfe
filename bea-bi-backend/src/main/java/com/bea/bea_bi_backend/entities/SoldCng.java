package com.bea.bea_bi_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "SOLD_CNG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoldCng {

    @EmbeddedId
    private SoldCngId id;

    @Column(name = "INIT_CNG", precision = 6, scale = 3)
    private BigDecimal initCng;

    @Column(name = "CUM_CNG", precision = 6, scale = 3)
    private BigDecimal cumCng;

    @Column(name = "PRIS_CNG", precision = 7, scale = 3)
    private BigDecimal prisCng;

    @Column(name = "SOLD_CNG", precision = 7, scale = 3)
    private BigDecimal soldCng;

    @Column(name = "CNG_NJUSTIF")
    private Integer cngNjustif;

    @Column(name = "CNG_JUSTIF")
    private Integer cngJustif;

    @Column(name = "BONUS_CNG", precision = 6, scale = 3)
    private BigDecimal bonusCng;

    @Column(name = "NBR_HEURS_INIT")
    private Long nbrHeursInit;

    @Column(name = "NBR_HEURS_PRIS")
    private Long nbrHeursPris;

    @Column(name = "NBR_HEURS_SOLD")
    private Long nbrHeursSold;

    @Column(name = "ID_SOLD_CNG")
    private Long idSoldCng;
}