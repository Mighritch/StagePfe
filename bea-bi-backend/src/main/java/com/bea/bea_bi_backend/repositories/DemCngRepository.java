package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.DemCng;
import com.bea.bea_bi_backend.entities.DemCngId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DemCngRepository extends JpaRepository<DemCng, DemCngId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT d FROM DemCng d WHERE d.id.codSoc = :soc")
    List<DemCng> findByCodSoc(@Param("soc") String codSoc);

    @Query("SELECT d FROM DemCng d WHERE d.id.codSoc = :soc AND d.id.matPers = :mat")
    List<DemCng> findBySocieteAndMatricule(
            @Param("soc") String codSoc,
            @Param("mat") String matPers);

    @Query("SELECT d FROM DemCng d WHERE d.id.codSoc = :soc AND d.etatCng = :etat")
    List<DemCng> findBySocieteAndEtat(
            @Param("soc") String codSoc,
            @Param("etat") String etatCng);

    @Query("SELECT d FROM DemCng d WHERE d.id.codSoc = :soc AND d.codeM = :motif")
    List<DemCng> findBySocieteAndMotif(
            @Param("soc") String codSoc,
            @Param("motif") String codeM);

    // ── Par période de début de congé ─────────────────────────────────────────

    @Query("SELECT d FROM DemCng d WHERE d.id.codSoc = :soc " +
            "AND d.datDebut BETWEEN :debut AND :fin")
    List<DemCng> findBySocieteAndPeriodeDebut(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT d FROM DemCng d WHERE d.id.codSoc = :soc " +
            "AND d.datDcng BETWEEN :debut AND :fin")
    List<DemCng> findBySocieteAndPeriodeDemande(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Par service ───────────────────────────────────────────────────────────

    @Query("SELECT d FROM DemCng d WHERE d.id.codSoc = :soc AND d.codServ = :serv " +
            "AND d.datDebut BETWEEN :debut AND :fin")
    List<DemCng> findBySocieteAndServiceAndPeriode(
            @Param("soc") String codSoc,
            @Param("serv") String codServ,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Analytique absences ───────────────────────────────────────────────────

    @Query("SELECT d.codeM, SUM(d.nbrJours) FROM DemCng d " +
            "WHERE d.id.codSoc = :soc AND d.datDebut BETWEEN :debut AND :fin " +
            "AND d.etatCng = 'V' GROUP BY d.codeM")
    List<Object[]> sumJoursByMotifAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT d.codServ, SUM(d.nbrJours) FROM DemCng d " +
            "WHERE d.id.codSoc = :soc AND d.datDebut BETWEEN :debut AND :fin " +
            "AND d.etatCng = 'V' GROUP BY d.codServ")
    List<Object[]> sumJoursByServiceAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT d.anneeCng, d.codeM, SUM(d.nbrJours), COUNT(d) FROM DemCng d " +
            "WHERE d.id.codSoc = :soc AND d.etatCng = 'V' " +
            "GROUP BY d.anneeCng, d.codeM ORDER BY d.anneeCng")
    List<Object[]> sumJoursByAnneeAndMotif(@Param("soc") String codSoc);

    // ── Demandes en attente de validation ─────────────────────────────────────

    @Query("SELECT d FROM DemCng d WHERE d.id.codSoc = :soc " +
            "AND d.etatCng NOT IN ('V', 'R') ORDER BY d.datDcng ASC")
    List<DemCng> findDemandesEnAttente(@Param("soc") String codSoc);
}