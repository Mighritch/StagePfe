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
import java.time.LocalDate;

@Entity
@Table(name = "LIG_PRET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigPret {

    @EmbeddedId
    private LigPretId id;

    @Column(name = "COD_TYP_BUL")
    private String codTypBul;

    @Column(name = "MOIS_PRET_PREVU")
    private LocalDate moisPretPrevu;

    @Column(name = "MOIS_PRET")
    private LocalDate moisPret;

    @Column(name = "MNT_PERIOD", precision = 12, scale = 3)
    private BigDecimal mntPeriod;

    @Column(name = "MNT_INT", precision = 12, scale = 3)
    private BigDecimal mntInt;

    @Column(name = "INT_GRACE", precision = 12, scale = 3)
    private BigDecimal intGrace;

    @Column(name = "CAP_REST", precision = 12, scale = 3)
    private BigDecimal capRest;

    @Column(name = "VAL_PRET")
    private String valPret;

    @Column(name = "IMPAYE")
    private String impaye;

    @Column(name = "NATURE_ETAT_PRET")
    private String natureEtatPret;

    @Column(name = "NUM_TRANCH")
    private Integer numTranch;

    @Column(name = "ID_LIG_PRET")
    private Long idLigPret;
}