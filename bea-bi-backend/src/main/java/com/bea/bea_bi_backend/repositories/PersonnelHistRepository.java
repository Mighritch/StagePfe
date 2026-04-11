package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.PersonnelHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonnelHistRepository extends JpaRepository<PersonnelHist, String> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    List<PersonnelHist> findByCodSoc(String codSoc);

    List<PersonnelHist> findByCodServ(String codServ);

    List<PersonnelHist> findByCodGrad(String codGrad);

    List<PersonnelHist> findByEtatAct(String etatAct);

    List<PersonnelHist> findByCodSocAndEtatAct(String codSoc, String etatAct);

    // ── Effectif actif ────────────────────────────────────────────────────────

    @Query("SELECT p FROM PersonnelHist p WHERE p.codSoc = :soc AND p.etatAct = 'A'")
    List<PersonnelHist> findActifsBySociete(@Param("soc") String codSoc);

    @Query("SELECT p FROM PersonnelHist p WHERE p.codSoc = :soc AND p.etatAct = 'A' AND p.codServ = :serv")
    List<PersonnelHist> findActifsBySocieteAndService(
            @Param("soc") String codSoc,
            @Param("serv") String codServ);

    @Query("SELECT p FROM PersonnelHist p WHERE p.codSoc = :soc AND p.etatAct = 'A' AND p.codGrad = :grad")
    List<PersonnelHist> findActifsBySocieteAndGrade(
            @Param("soc") String codSoc,
            @Param("grad") String codGrad);

    // ── Comptages analytiques ─────────────────────────────────────────────────

    @Query("SELECT COUNT(p) FROM PersonnelHist p WHERE p.codSoc = :soc AND p.etatAct = 'A'")
    Long countActifsBySociete(@Param("soc") String codSoc);

    @Query("SELECT p.codServ, COUNT(p) FROM PersonnelHist p " +
            "WHERE p.codSoc = :soc AND p.etatAct = 'A' GROUP BY p.codServ")
    List<Object[]> countActifsByService(@Param("soc") String codSoc);

    @Query("SELECT p.codGrad, COUNT(p) FROM PersonnelHist p " +
            "WHERE p.codSoc = :soc AND p.etatAct = 'A' GROUP BY p.codGrad")
    List<Object[]> countActifsByGrade(@Param("soc") String codSoc);

    @Query("SELECT p.sexe, COUNT(p) FROM PersonnelHist p " +
            "WHERE p.codSoc = :soc AND p.etatAct = 'A' GROUP BY p.sexe")
    List<Object[]> countActifsBySexe(@Param("soc") String codSoc);

    @Query("SELECT p.admTech, COUNT(p) FROM PersonnelHist p " +
            "WHERE p.codSoc = :soc AND p.etatAct = 'A' GROUP BY p.admTech")
    List<Object[]> countActifsByAdmTech(@Param("soc") String codSoc);

    // ── Entrées / Départs sur une période ────────────────────────────────────

    @Query("SELECT p FROM PersonnelHist p WHERE p.codSoc = :soc " +
            "AND p.datEnt BETWEEN :debut AND :fin")
    List<PersonnelHist> findEntreesBySocieteAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT p FROM PersonnelHist p WHERE p.codSoc = :soc " +
            "AND p.datDepart BETWEEN :debut AND :fin")
    List<PersonnelHist> findDepartsBySocieteAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Handicap ──────────────────────────────────────────────────────────────

    @Query("SELECT p FROM PersonnelHist p WHERE p.codSoc = :soc AND p.handicap = 'O'")
    List<PersonnelHist> findHandicapesBySociete(@Param("soc") String codSoc);
}