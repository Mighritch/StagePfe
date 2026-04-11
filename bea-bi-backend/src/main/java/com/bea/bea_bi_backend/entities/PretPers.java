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
@Table(name = "PRET_PERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PretPers {

    @EmbeddedId
    private PretPersId id;

    @Column(name = "COD_SERV")
    private String codServ;

    @Column(name = "COD_GRAD")
    private String codGrad;

    @Column(name = "ADM_TECH")
    private String admTech;

    @Column(name = "COD_ETAT_PRET")
    private String codEtatPret;

    @Column(name = "TYP_PRET")
    private String typPret;

    @Column(name = "ABRV_FIXE")
    private String abrvFixe;

    @Column(name = "PRT_DAT_ACC")
    private LocalDate prtDatAcc;

    @Column(name = "PRT_MNT_GLB", precision = 12, scale = 3)
    private BigDecimal prtMntGlb;

    @Column(name = "PRT_TAUX", precision = 6, scale = 2)
    private BigDecimal prtTaux;

    @Column(name = "PRT_ECH")
    private Integer prtEch;

    @Column(name = "PRT_DAT_DEB")
    private LocalDate prtDatDeb;

    @Column(name = "PRT_DAT_FIN")
    private LocalDate prtDatFin;

    @Column(name = "PRT_INTERET", precision = 12, scale = 3)
    private BigDecimal prtInteret;

    @Column(name = "PRT_MNT_REM", precision = 12, scale = 3)
    private BigDecimal prtMntRem;

    @Column(name = "REM_MEN", precision = 12, scale = 3)
    private BigDecimal remMen;

    @Column(name = "PRT_RENDU", precision = 12, scale = 3)
    private BigDecimal prtRendu;

    @Column(name = "OBJET_PRET")
    private String objetPret;

    @Column(name = "DAT_DEBLOCAGE")
    private LocalDate datDeblocage;

    @Column(name = "PRT_MNT_DEBLOQUE", precision = 12, scale = 3)
    private BigDecimal prtMntDebloque;

    @Column(name = "ID_PRET_PERS")
    private Long idPretPers;
}