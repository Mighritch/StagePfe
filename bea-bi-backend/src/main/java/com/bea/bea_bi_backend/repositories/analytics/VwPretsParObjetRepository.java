package com.bea.bea_bi_backend.repositories.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwPretsParObjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwPretsParObjetRepository
        extends JpaRepository<VwPretsParObjet, String> {

    @Query("SELECT v FROM VwPretsParObjet v " +
            "WHERE (:service IS NULL OR v.libServ = :service) " +
            "AND (:annee IS NULL OR v.annee = :annee) " +
            "AND (:objet IS NULL OR v.objetPret = :objet)")
    List<VwPretsParObjet> findByFiltres(
            @Param("service") String service,
            @Param("annee") Integer annee,
            @Param("objet") String objet);
}