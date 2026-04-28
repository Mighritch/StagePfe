package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwTauxInteretPret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwTauxInteretPretRepository
        extends JpaRepository<VwTauxInteretPret, String> {

    @Query("SELECT v FROM VwTauxInteretPret v " +
            "WHERE (:typPret IS NULL OR v.libPret = :typPret) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwTauxInteretPret> findByFiltres(
            @Param("typPret") String typPret,
            @Param("annee") Integer annee);
}