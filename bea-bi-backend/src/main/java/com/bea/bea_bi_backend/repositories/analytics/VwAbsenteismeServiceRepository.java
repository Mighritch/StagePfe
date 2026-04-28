package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwAbsenteismeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwAbsenteismeServiceRepository
        extends JpaRepository<VwAbsenteismeService, String> {

    @Query("SELECT v FROM VwAbsenteismeService v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwAbsenteismeService> findByFiltres(
            @Param("service") String service,
            @Param("annee") Integer annee);
}