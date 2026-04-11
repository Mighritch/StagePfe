package com.bea.bea_bi_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeId implements Serializable {

    @Column(name = "COD_CATEG")
    private String codCateg;

    @Column(name = "COD_CAT")
    private String codCat;

    @Column(name = "COD_GRAD")
    private String codGrad;
}