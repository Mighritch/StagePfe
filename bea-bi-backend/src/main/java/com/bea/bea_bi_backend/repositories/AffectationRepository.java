package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffectationRepository extends JpaRepository<Affectation, String> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    List<Affectation> findByRegime(String regime);

    List<Affectation> findByTypAffect(String typAffect);

    List<Affectation> findByNatAffect(String natAffect);

    // ── Référentiel filtré ────────────────────────────────────────────────────

    @Query("SELECT a FROM Affectation a WHERE a.reserve IS NULL OR a.reserve = 'N'")
    List<Affectation> findAffectationsActives();

    @Query("SELECT a FROM Affectation a WHERE a.typAffect = :type AND (a.reserve IS NULL OR a.reserve = 'N')")
    List<Affectation> findActivesParType(@Param("type") String typAffect);

    // ── Recherche par libellé ─────────────────────────────────────────────────

    @Query("SELECT a FROM Affectation a WHERE UPPER(a.libAffect) LIKE UPPER(CONCAT('%', :lib, '%'))")
    List<Affectation> findByLibelleContaining(@Param("lib") String libAffect);

    // ── Statistiques ─────────────────────────────────────────────────────────

    @Query("SELECT a.typAffect, COUNT(a) FROM Affectation a GROUP BY a.typAffect")
    List<Object[]> countByTypeAffectation();

    @Query("SELECT a.regime, COUNT(a) FROM Affectation a GROUP BY a.regime")
    List<Object[]> countByRegime();
}