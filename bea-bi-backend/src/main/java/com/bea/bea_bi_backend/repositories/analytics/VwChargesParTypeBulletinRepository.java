package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwChargesParTypeBulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwChargesParTypeBulletinRepository
        extends JpaRepository<VwChargesParTypeBulletin, String> {

    @Query("SELECT v FROM VwChargesParTypeBulletin v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwChargesParTypeBulletin> findByFiltres(
            @Param("service") String service,
            @Param("annee") Integer annee);
}