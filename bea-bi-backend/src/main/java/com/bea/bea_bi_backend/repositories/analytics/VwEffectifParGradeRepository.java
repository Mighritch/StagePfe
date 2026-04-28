package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwEffectifParGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwEffectifParGradeRepository
        extends JpaRepository<VwEffectifParGrade, String> {

    @Query("SELECT v FROM VwEffectifParGrade v " +
            "WHERE (:grade IS NULL OR v.libGrad = :grade) " +
            "AND (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwEffectifParGrade> findByFiltres(
            @Param("grade") String grade,
            @Param("service") String service,
            @Param("annee") Integer annee);
}