package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwDureeMoyenneConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwDureeMoyenneCongeRepository
        extends JpaRepository<VwDureeMoyenneConge, String> {

    @Query("SELECT v FROM VwDureeMoyenneConge v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee) " +
            "AND (:motif IS NULL OR v.libMot = :motif)")
    List<VwDureeMoyenneConge> findByFiltres(
            @Param("service") String service,
            @Param("annee") Integer annee,
            @Param("motif") String motif);
}