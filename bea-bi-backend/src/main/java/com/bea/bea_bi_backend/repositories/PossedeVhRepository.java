package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.PossedeVh;
import com.bea.bea_bi_backend.entities.PossedeVhId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PossedeVhRepository extends JpaRepository<PossedeVh, PossedeVhId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT p FROM PossedeVh p WHERE p.id.codSoc = :soc")
    List<PossedeVh> findByCodSoc(@Param("soc") String codSoc);

    @Query("SELECT p FROM PossedeVh p WHERE p.id.codSoc = :soc AND p.id.matPers = :mat")
    List<PossedeVh> findBySocieteAndMatricule(
            @Param("soc") String codSoc,
            @Param("mat") String matPers);

    @Query("SELECT p FROM PossedeVh p WHERE p.id.codSoc = :soc AND p.id.abrvFixe = :abrv")
    List<PossedeVh> findBySocieteAndRubrique(
            @Param("soc") String codSoc,
            @Param("abrv") String abrvFixe);

    // ── Par période ───────────────────────────────────────────────────────────

    @Query("SELECT p FROM PossedeVh p WHERE p.id.codSoc = :soc " +
            "AND p.id.dtBul BETWEEN :debut AND :fin")
    List<PossedeVh> findBySocieteAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT p FROM PossedeVh p WHERE p.id.codSoc = :soc " +
            "AND p.id.dtBul BETWEEN :debut AND :fin AND p.id.abrvFixe = :abrv")
    List<PossedeVh> findBySocieteAndRubriqueAndPeriode(
            @Param("soc") String codSoc,
            @Param("abrv") String abrvFixe,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Analytique masse salariale ────────────────────────────────────────────

    @Query("SELECT p.id.abrvFixe, SUM(p.montant) FROM PossedeVh p " +
            "WHERE p.id.codSoc = :soc AND p.id.dtBul BETWEEN :debut AND :fin " +
            "AND p.typePar = :type GROUP BY p.id.abrvFixe")
    List<Object[]> sumMontantByRubriqueAndPeriode(
            @Param("soc") String codSoc,
            @Param("type") String typePar,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT p.codServ, SUM(p.montant) FROM PossedeVh p " +
            "WHERE p.id.codSoc = :soc AND p.id.dtBul BETWEEN :debut AND :fin " +
            "GROUP BY p.codServ")
    List<Object[]> sumMontantByServiceAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT p.id.dtBul, SUM(p.montant) FROM PossedeVh p " +
            "WHERE p.id.codSoc = :soc AND p.id.dtBul BETWEEN :debut AND :fin " +
            "GROUP BY p.id.dtBul ORDER BY p.id.dtBul")
    List<Object[]> sumMontantByMoisAndPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // ── Masse salariale totale ────────────────────────────────────────────────

    @Query("SELECT SUM(p.montant) FROM PossedeVh p " +
            "WHERE p.id.codSoc = :soc AND p.id.dtBul BETWEEN :debut AND :fin")
    java.math.BigDecimal sumMasseSalarialeByPeriode(
            @Param("soc") String codSoc,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);
}