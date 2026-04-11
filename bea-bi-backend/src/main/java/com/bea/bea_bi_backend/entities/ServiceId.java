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
public class ServiceId implements Serializable {

    @Column(name = "COD_SOC")
    private String codSoc;

    @Column(name = "COD_SERV")
    private String codServ;
}