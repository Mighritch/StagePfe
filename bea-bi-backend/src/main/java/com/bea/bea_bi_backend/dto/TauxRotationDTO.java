package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TauxRotationDTO {
    private LocalDate debut;
    private LocalDate fin;
    private long nombreEntrees;
    private long nombreDeparts;
    private long effectifMoyen;
    private double tauxRotation; // en %
}