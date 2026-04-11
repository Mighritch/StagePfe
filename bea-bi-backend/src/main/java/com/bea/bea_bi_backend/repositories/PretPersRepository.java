package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.PretPers;
import com.bea.bea_bi_backend.entities.PretPersId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PretPersRepository extends JpaRepository<PretPers, PretPersId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT p FROM PretPers p WHERE p.id.codSoc = :soc")
    List<PretPers> findByCodSoc(@Param("soc") String codSoc);

    @Query("SELECT p FROM PretPers p WHERE p.id.codSoc = :soc AND p.id.matPers = :mat")
    List<PretPers> findBySocieteAndMatricule(
            @Param("soc") String codSoc,
            @Param("mat") String matPers);

    @Query("SELECT p FROM PretPers p WHERE p.id.codSoc = :soc AND p.codEtatPret = :etat")
    List<PretPers> findBySocieteAndEtat(
            @Param("soc") String codSoc,
            @Param("etat") String codEtatPret);

    @Query("SELECT p FROM PretPers p WHERE p.id.codSoc = :soc AND p.typPret = :typ")
    List<PretPers> findBySocieteAndType(
            @Param("soc") String codSoc,
            @Param("typ") String typPret);

    // ── Prêts en cours ────────────────────────────────────────────────────────

    @Query("SELECT p FROM PretPers p WHERE p.id.codSoc = :soc " +
            "AND p.codEtatPret = 'EC' ORDER BY p.prtDatAcc DESC")
    List<PretPers> findPretsEnCoursBySociete(@Param("soc") String codSoc);

    @Query("SELECT p FROM PretPers p WHERE p.id.codSoc = :soc AND p.codServ = :serv " +
            "AND p.codEtatPret = 'EC'")
    List<PretPers> findPretsEnCoursByService(
            @Param("soc") String codSoc,
            @Param("serv") String codServ);

    // ── Par période d'accord ──────────────────────────────────────────────────

    @Query("SELECT p FROM PretPers p WHERE p.id.codSoc = :soc " +
            "AND p.prtDatAcc BETWEEN :debut AND :fin")
    List<PretPers> findBySocieteAndPeriodeAccord(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Analytique crédit ─────────────────────────────────────────────────────

    @Query("SELECT p.typPret, COUNT(p), SUM(p.prtMntGlb) FROM PretPers p " +
            "WHERE p.id.codSoc = :soc AND p.codEtatPret = 'EC' GROUP BY p.typPret")
    List<Object[]> statsPretsEnCoursByType(@Param("soc") String codSoc);

    @Query("SELECT p.codServ, COUNT(p), SUM(p.prtMntGlb), SUM(p.prtMntRem) FROM PretPers p " +
            "WHERE p.id.codSoc = :soc AND p.codEtatPret = 'EC' GROUP BY p.codServ")
    List<Object[]> statsPretsEnCoursByService(@Param("soc") String codSoc);

    @Query("SELECT p.codGrad, COUNT(p), SUM(p.prtMntGlb) FROM PretPers p " +
            "WHERE p.id.codSoc = :soc AND p.codEtatPret = 'EC' GROUP BY p.codGrad")
    List<Object[]> statsPretsEnCoursByGrade(@Param("soc") String codSoc);

    // ── Encours total ─────────────────────────────────────────────────────────

    @Query("SELECT SUM(p.prtMntGlb - p.prtMntRem) FROM PretPers p " +
            "WHERE p.id.codSoc = :soc AND p.codEtatPret = 'EC'")
    BigDecimal sumEncoursTotalBySociete(@Param("soc") String codSoc);

    @Query("SELECT SUM(p.prtMntGlb) FROM PretPers p " +
            "WHERE p.id.codSoc = :soc AND p.prtDatAcc BETWEEN :debut AND :fin")
    BigDecimal sumMontantAccordeBySocieteAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Évolution mensuelle ───────────────────────────────────────────────────

    @Query("SELECT FUNCTION('TO_CHAR', p.prtDatAcc, 'YYYY-MM'), " +
            "COUNT(p), SUM(p.prtMntGlb) FROM PretPers p " +
            "WHERE p.id.codSoc = :soc AND p.prtDatAcc BETWEEN :debut AND :fin " +
            "GROUP BY FUNCTION('TO_CHAR', p.prtDatAcc, 'YYYY-MM') " +
            "ORDER BY FUNCTION('TO_CHAR', p.prtDatAcc, 'YYYY-MM')")
    List<Object[]> evolutionMensuellePretsBySociete(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Taux impayés ──────────────────────────────────────────────────────────

    @Query("SELECT p FROM PretPers p WHERE p.id.codSoc = :soc " +
            "AND p.codEtatPret = 'EC' " +
            "AND (p.prtMntGlb - p.prtMntRem) > (p.remMen * :seuilMois)")
    List<PretPers> findPretsArisques(
            @Param("soc") String codSoc,
            @Param("seuilMois") int seuilMoisRetard);
}