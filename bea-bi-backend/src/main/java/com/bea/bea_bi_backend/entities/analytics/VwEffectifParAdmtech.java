package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "VW_EFFECTIF_PAR_ADMTECH")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwEffectifParAdmtech {

    @Id
    @Column(name = "LIB_ADM_TECH")
    private String libAdmTech;

    @Column(name = "COD_CAT")
    private String codCat;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    // Ajout des colonnes de filtrage pour corriger le Repository
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "ANNEE")
    private Integer annee;
}