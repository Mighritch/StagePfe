package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwEncoursParTypePret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwEncoursParTypePretRepository
        extends JpaRepository<VwEncoursParTypePret, String> {

    @Query("SELECT v FROM VwEncoursParTypePret v " +
            "WHERE (:typPret IS NULL OR v.typPret = :typPret) " +
            "AND (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee)")
    List<VwEncoursParTypePret> findByFiltres(
            @Param("typPret") String typPret,
            @Param("service") String service,
            @Param("annee") Integer annee);
}