package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwEffectifServiceGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwEffectifServiceGradeRepository
        extends JpaRepository<VwEffectifServiceGrade, String> {

    @Query("SELECT v FROM VwEffectifServiceGrade v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:grade IS NULL OR v.libGrad = :grade) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwEffectifServiceGrade> findByFiltres(
            @Param("service") String service,
            @Param("grade") String grade,
            @Param("annee") Integer annee);
}