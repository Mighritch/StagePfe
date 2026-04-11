package com.bea.bea_bi_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresenceDTO {
    private String code;        // codServ ou codGrad selon le contexte
    private BigDecimal totalPresence;
}