package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_MASSE_SALARIALE_SERVICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwMasseSalarialeService {

    @Id
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "MASSE_SALARIALE", precision = 12, scale = 2)
    private BigDecimal masseSalariale;

    @Column(name = "SALAIRE_MOYEN", precision = 12, scale = 2)
    private BigDecimal salaireMoyen;

    @Column(name = "NB_EMPLOYES")
    private Long nbEmployes;

    // Ajout de la colonne manquante pour résoudre l'erreur du Repository
    @Column(name = "ANNEE")
    private Integer annee;
}