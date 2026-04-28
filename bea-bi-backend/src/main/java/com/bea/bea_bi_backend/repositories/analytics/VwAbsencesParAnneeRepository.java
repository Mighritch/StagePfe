package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwAbsencesParAnnee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwAbsencesParAnneeRepository
        extends JpaRepository<VwAbsencesParAnnee, Integer> {

    @Query("SELECT v FROM VwAbsencesParAnnee v " +
            "WHERE (:annee IS NULL OR v.anneeCng = :annee) " +
            "AND (:typCng IS NULL OR v.typCng = :typCng) " +
            "AND (:service IS NULL OR v.libServ = :service)")
    List<VwAbsencesParAnnee> findByFiltres(
            @Param("annee") Integer annee,
            @Param("typCng") String typCng,
            @Param("service") String service);
}