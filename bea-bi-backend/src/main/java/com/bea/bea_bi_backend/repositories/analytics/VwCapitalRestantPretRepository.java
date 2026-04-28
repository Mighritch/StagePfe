package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwCapitalRestantPret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwCapitalRestantPretRepository
        extends JpaRepository<VwCapitalRestantPret, String> {

    @Query("SELECT v FROM VwCapitalRestantPret v " +
            "WHERE (:typPret IS NULL OR v.libPret = :typPret) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwCapitalRestantPret> findByFiltres(
            @Param("typPret") String typPret,
            @Param("annee") Integer annee);
}