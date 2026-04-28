package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwMasseSalarialeMois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VwMasseSalarialeMoisRepository
        extends JpaRepository<VwMasseSalarialeMois, Date> {

    @Query("SELECT v FROM VwMasseSalarialeMois v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee) " +
            "AND (:mois IS NULL OR v.moisNum = :mois)")
    List<VwMasseSalarialeMois> findByFiltres(
            @Param("service") String service,
            @Param("annee") Integer annee,
            @Param("mois") Integer mois);
}