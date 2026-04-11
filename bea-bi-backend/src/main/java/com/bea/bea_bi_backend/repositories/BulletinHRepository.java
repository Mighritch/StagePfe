package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.BulletinH;
import com.bea.bea_bi_backend.entities.BulletinHId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BulletinHRepository extends JpaRepository<BulletinH, BulletinHId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT b FROM BulletinH b WHERE b.id.codSoc = :soc")
    List<BulletinH> findByCodSoc(@Param("soc") String codSoc);

    @Query("SELECT b FROM BulletinH b WHERE b.id.codSoc = :soc AND b.id.matPers = :mat")
    List<BulletinH> findBySocieteAndMatricule(
            @Param("soc") String codSoc,
            @Param("mat") String matPers);

    @Query("SELECT b FROM BulletinH b WHERE b.id.codSoc = :soc AND b.id.codTypBul = :typ")
    List<BulletinH> findBySocieteAndTypeBulletin(
            @Param("soc") String codSoc,
            @Param("typ") String codTypBul);

    // ── Par période ───────────────────────────────────────────────────────────

    @Query("SELECT b FROM BulletinH b WHERE b.id.codSoc = :soc " +
            "AND b.id.dtBul BETWEEN :debut AND :fin")
    List<BulletinH> findBySocieteAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT b FROM BulletinH b WHERE b.id.codSoc = :soc " +
            "AND b.id.dtBul BETWEEN :debut AND :fin AND b.codServ = :serv")
    List<BulletinH> findBySocieteAndServiceAndPeriode(
            @Param("soc") String codSoc,
            @Param("serv") String codServ,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT b FROM BulletinH b WHERE b.id.codSoc = :soc " +
            "AND b.id.dtBul BETWEEN :debut AND :fin AND b.codGrad = :grad")
    List<BulletinH> findBySocieteAndGradeAndPeriode(
            @Param("soc") String codSoc,
            @Param("grad") String codGrad,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Analytique présence ───────────────────────────────────────────────────

    @Query("SELECT b.codServ, SUM(b.presence) FROM BulletinH b " +
            "WHERE b.id.codSoc = :soc AND b.id.dtBul BETWEEN :debut AND :fin " +
            "GROUP BY b.codServ")
    List<Object[]> sumPresenceByServiceAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT b.codGrad, SUM(b.presence) FROM BulletinH b " +
            "WHERE b.id.codSoc = :soc AND b.id.dtBul BETWEEN :debut AND :fin " +
            "GROUP BY b.codGrad")
    List<Object[]> sumPresenceByGradeAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Dernier bulletin par matricule ────────────────────────────────────────

    @Query("SELECT b FROM BulletinH b WHERE b.id.codSoc = :soc AND b.id.matPers = :mat " +
            "AND b.id.dtBul = (SELECT MAX(b2.id.dtBul) FROM BulletinH b2 " +
            "WHERE b2.id.codSoc = :soc AND b2.id.matPers = :mat)")
    List<BulletinH> findDernierBulletinByMatricule(
            @Param("soc") String codSoc,
            @Param("mat") String matPers);
}