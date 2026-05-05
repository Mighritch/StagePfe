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
           "WHERE (:annee IS NULL OR v.annee = :annee) " +
           "ORDER BY v.annee ASC")
    List<VwEvolutionMasseSalariale> findByFiltres(@Param("annee") Integer annee);

    // Query de secours au cas où le view pose encore problème
    @Query(value = """
        SELECT annee, masse_salariale, masse_prec,
               CASE WHEN masse_prec = 0 OR masse_prec IS NULL THEN 0 
                    ELSE ROUND((masse_salariale - masse_prec)/masse_prec * 100, 2) 
               END as variation_pct
        FROM vw_evolution_masse_salariale 
        WHERE (:annee IS NULL OR annee = :annee)
        ORDER BY annee ASC
        """, nativeQuery = true)
    List<Object[]> findByFiltresSafe(@Param("annee") Integer annee);
}