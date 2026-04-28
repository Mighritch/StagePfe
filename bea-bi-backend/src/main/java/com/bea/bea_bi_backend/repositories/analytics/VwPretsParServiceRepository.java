package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwPretsParService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwPretsParServiceRepository
        extends JpaRepository<VwPretsParService, String> {

    @Query("SELECT v FROM VwPretsParService v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:typPret IS NULL OR v.typPret = :typPret) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwPretsParService> findByFiltres(
            @Param("service") String service,
            @Param("typPret") String typPret,
            @Param("annee") Integer annee);
}