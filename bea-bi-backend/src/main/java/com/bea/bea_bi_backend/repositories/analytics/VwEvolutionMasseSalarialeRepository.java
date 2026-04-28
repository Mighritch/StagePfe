package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwEvolutionMasseSalariale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwEvolutionMasseSalarialeRepository
        extends JpaRepository<VwEvolutionMasseSalariale, Integer> {

    @Query("SELECT v FROM VwEvolutionMasseSalariale v " +
            "WHERE (:annee IS NULL OR v.annee = :annee)")
    List<VwEvolutionMasseSalariale> findByFiltres(
            @Param("annee") Integer annee);
}