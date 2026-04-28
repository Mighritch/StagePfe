package com.bea.bea_bi_backend.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VwEffectifParAdmtechDto {
    private String libAdmTech;
    private String codCat;
    private Long nbEmployes;
    private String libServ;
    private Integer annee;
}