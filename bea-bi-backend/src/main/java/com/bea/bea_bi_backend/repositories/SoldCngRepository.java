package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.SoldCng;
import com.bea.bea_bi_backend.entities.SoldCngId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoldCngRepository extends JpaRepository<SoldCng, SoldCngId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT s FROM SoldCng s WHERE s.id.codSoc = :soc AND s.id.matPers = :mat")
    List<SoldCng> findBySocieteAndMatricule(
            @Param("soc") String codSoc,
            @Param("mat") String matPers);

    @Query("SELECT s FROM SoldCng s WHERE s.id.codSoc = :soc AND s.id.anneeCng = :annee")
    List<SoldCng> findBySocieteAndAnnee(
            @Param("soc") String codSoc,
            @Param("annee") Integer anneeCng);

    @Query("SELECT s FROM SoldCng s WHERE s.id.codSoc = :soc " +
            "AND s.id.matPers = :mat AND s.id.anneeCng = :annee AND s.id.typCng = :typ")
    Optional<SoldCng> findSoldeExact(
            @Param("soc") String codSoc,
            @Param("mat") String matPers,
            @Param("annee") Integer anneeCng,
            @Param("typ") String typCng);

    // ── Soldes restants pour une année ────────────────────────────────────────

    @Query("SELECT s FROM SoldCng s WHERE s.id.codSoc = :soc " +
            "AND s.id.anneeCng = :annee AND s.soldCng > 0")
    List<SoldCng> findSoldesPositifsByAnnee(
            @Param("soc") String codSoc,
            @Param("annee") Integer anneeCng);

    // ── Analytique soldes ─────────────────────────────────────────────────────

    @Query("SELECT s.id.typCng, SUM(s.soldCng), AVG(s.soldCng) FROM SoldCng s " +
            "WHERE s.id.codSoc = :soc AND s.id.anneeCng = :annee GROUP BY s.id.typCng")
    List<Object[]> statsSoldesByTypeConge(
            @Param("soc") String codSoc,
            @Param("annee") Integer anneeCng);

    @Query("SELECT s.id.anneeCng, s.id.typCng, SUM(s.prisCng), SUM(s.soldCng) FROM SoldCng s " +
            "WHERE s.id.codSoc = :soc GROUP BY s.id.anneeCng, s.id.typCng ORDER BY s.id.anneeCng")
    List<Object[]> evolutionCongesParAnnee(@Param("soc") String codSoc);

    // ── Absences non justifiées ───────────────────────────────────────────────

    @Query("SELECT SUM(s.cngNjustif) FROM SoldCng s " +
            "WHERE s.id.codSoc = :soc AND s.id.anneeCng = :annee")
    Integer sumAbsencesNonJustifieesParAnnee(
            @Param("soc") String codSoc,
            @Param("annee") Integer anneeCng);
}