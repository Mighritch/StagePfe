package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwMasseSalarialeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwMasseSalarialeServiceRepository
        extends JpaRepository<VwMasseSalarialeService, String> {

    @Query("SELECT v FROM VwMasseSalarialeService v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwMasseSalarialeService> findByFiltres(
            @Param("service") String service,
            @Param("annee") Integer annee);
}