package com.bea.bea_bi_backend.entities.analytics;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VW_PRETS_PAR_SERVICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Immutable
public class VwPretsParService {

    @Id
    @Column(name = "LIB_SERV")
    private String libServ;

    @Column(name = "NB_PRETS")
    private Long nbPrets;

    @Column(name = "TOTAL_CREDIT", precision = 12, scale = 3)
    private BigDecimal totalCredit;

    @Column(name = "MONTANT_MOYEN", precision = 12, scale = 3)
    private BigDecimal montantMoyen;

    // Colonnes ajoutées pour permettre le filtrage dans le Repository
    @Column(name = "TYP_PRET")
    private String typPret;

    @Column(name = "ANNEE")
    private Integer annee;
}