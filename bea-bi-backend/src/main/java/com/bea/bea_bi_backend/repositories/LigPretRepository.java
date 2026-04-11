package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.LigPret;
import com.bea.bea_bi_backend.entities.LigPretId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LigPretRepository extends JpaRepository<LigPret, LigPretId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT l FROM LigPret l WHERE l.id.codSoc = :soc AND l.id.matPers = :mat " +
            "AND l.id.codPret = :pret ORDER BY l.id.lPret")
    List<LigPret> findEcheancierByPret(
            @Param("soc") String codSoc,
            @Param("mat") String matPers,
            @Param("pret") Long codPret);

    @Query("SELECT l FROM LigPret l WHERE l.id.codSoc = :soc AND l.id.matPers = :mat")
    List<LigPret> findBySocieteAndMatricule(
            @Param("soc") String codSoc,
            @Param("mat") String matPers);

    // ── Échéances par période ─────────────────────────────────────────────────

    @Query("SELECT l FROM LigPret l WHERE l.id.codSoc = :soc " +
            "AND l.moisPretPrevu BETWEEN :debut AND :fin")
    List<LigPret> findEcheancesPrevuesByPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT l FROM LigPret l WHERE l.id.codSoc = :soc " +
            "AND l.moisPret BETWEEN :debut AND :fin")
    List<LigPret> findEcheancesReellesByPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Impayés ───────────────────────────────────────────────────────────────

    @Query("SELECT l FROM LigPret l WHERE l.id.codSoc = :soc AND l.impaye = 'O'")
    List<LigPret> findImpayes(@Param("soc") String codSoc);

    @Query("SELECT l.id.matPers, COUNT(l), SUM(l.mntPeriod) FROM LigPret l " +
            "WHERE l.id.codSoc = :soc AND l.impaye = 'O' GROUP BY l.id.matPers")
    List<Object[]> statsImpayesByMatricule(@Param("soc") String codSoc);

    // ── Analytique remboursements ─────────────────────────────────────────────

    @Query("SELECT l.moisPret, SUM(l.mntPeriod), SUM(l.mntInt), SUM(l.capRest) FROM LigPret l " +
            "WHERE l.id.codSoc = :soc AND l.moisPret BETWEEN :debut AND :fin " +
            "GROUP BY l.moisPret ORDER BY l.moisPret")
    List<Object[]> evolutionRemboursementsByPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT SUM(l.capRest) FROM LigPret l " +
            "WHERE l.id.codSoc = :soc AND l.valPret = 'O' " +
            "AND l.moisPretPrevu = :mois")
    BigDecimal sumCapitalRestantByMois(
            @Param("soc") String codSoc,
            @Param("mois") LocalDate mois);
}