package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwSalaireParGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwSalaireParGradeRepository
        extends JpaRepository<VwSalaireParGrade, String> {

    @Query("SELECT v FROM VwSalaireParGrade v " +
            "WHERE (:grade IS NULL OR v.libGrad = :grade) " +
            "AND (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwSalaireParGrade> findByFiltres(
            @Param("grade") String grade,
            @Param("service") String service,
            @Param("annee") Integer annee);
}