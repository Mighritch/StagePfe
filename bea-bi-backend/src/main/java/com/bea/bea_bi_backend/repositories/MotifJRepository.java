package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.MotifJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotifJRepository extends JpaRepository<MotifJ, String> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    List<MotifJ> findByTypCng(String typCng);

    List<MotifJ> findByNatureCng(String natureCng);

    List<MotifJ> findByTypeAbs(String typeAbs);

    List<MotifJ> findByNatCng(String natCng);

    // ── Motifs avec déduction salaire ─────────────────────────────────────────

    @Query("SELECT m FROM MotifJ m WHERE m.dedSal = 'O'")
    List<MotifJ> findAvecDeductionSalaire();

    @Query("SELECT m FROM MotifJ m WHERE m.dedCng = 'O'")
    List<MotifJ> findAvecDeductionConge();

    // ── Référentiel trié ──────────────────────────────────────────────────────

    @Query("SELECT m FROM MotifJ m ORDER BY m.typCng, m.codM")
    List<MotifJ> findAllOrderByTypeCng();

    // ── Statistiques ─────────────────────────────────────────────────────────

    @Query("SELECT m.typCng, COUNT(m) FROM MotifJ m GROUP BY m.typCng")
    List<Object[]> countByTypeCng();
}