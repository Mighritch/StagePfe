package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MouvementPersonnelDTO {
    private String matPers;
    private String nomPers;
    private String prenPers;
    private String codServ;
    private String codGrad;
    private LocalDate date;      // datEnt ou datDepart selon le contexte
    private String typeMouvement; // "ENTREE" ou "DEPART"
}