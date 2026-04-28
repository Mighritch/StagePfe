package com.bea.bea_bi_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MouvementPersonnelDTO {

    @NotBlank
    private String matPers;

    private String nomPers;
    private String prenPers;
    private String codServ;
    private String codGrad;

    private LocalDate date;
    private String typeMouvement; // "ENTREE" ou "DEPART"
}