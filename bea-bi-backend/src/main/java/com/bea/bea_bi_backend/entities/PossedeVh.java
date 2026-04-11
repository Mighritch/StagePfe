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
@Table(name = "POSSEDEVH")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PossedeVh {

    @EmbeddedId
    private PossedeVhId id;

    @Column(name = "DATEV")
    private LocalDate datev;

    @Column(name = "BUL_COD_TYP_BUL")
    private String bulCodTypBul;

    @Column(name = "MONTV", precision = 12, scale = 3)
    private BigDecimal montv;

    @Column(name = "MONTANT", precision = 12, scale = 3)
    private BigDecimal montant;

    @Column(name = "COD_SERV")
    private String codServ;

    @Column(name = "TYPE_PAR")
    private String typePar;

    @Column(name = "COD_NIV", precision = 4)
    private Integer codNiv;

    @Column(name = "NUM_NIV", precision = 3)
    private Integer numNiv;

    @Column(name = "NOMBRE", precision = 12, scale = 3)
    private BigDecimal nombre;

    @Column(name = "TAUX", precision = 7, scale = 3)
    private BigDecimal taux;

    @Column(name = "ID_POSSEDEVH")
    private Long idPossedevh;
}