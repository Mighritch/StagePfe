package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwSoldeCongeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwSoldeCongeTypeRepository
        extends JpaRepository<VwSoldeCongeType, String> {

    @Query("SELECT v FROM VwSoldeCongeType v " +
            "WHERE (:typCng IS NULL OR v.typCng = :typCng) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwSoldeCongeType> findByFiltres(
            @Param("typCng") String typCng,
            @Param("annee") Integer annee);
}