package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwTauxChargeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwTauxChargeServiceRepository
        extends JpaRepository<VwTauxChargeService, String> {

    @Query("SELECT v FROM VwTauxChargeService v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwTauxChargeService> findByFiltres(
            @Param("service") String service,
            @Param("annee") Integer annee);
}