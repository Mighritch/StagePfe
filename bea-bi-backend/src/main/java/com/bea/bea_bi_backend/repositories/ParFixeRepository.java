package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.ParFixe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParFixeRepository extends JpaRepository<ParFixe, String> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    List<ParFixe> findByTypePar(String typePar);

    List<ParFixe> findBySensImput(String sensImput);   // 'C' crédit / 'D' débit

    List<ParFixe> findByActive(String active);

    // ── Rubriques actives par type ────────────────────────────────────────────

    @Query("SELECT p FROM ParFixe p WHERE p.active = 'O' AND p.typePar = :type")
    List<ParFixe> findActivesParType(@Param("type") String typePar);

    // ── Gains (crédit) / Retenues (débit) ─────────────────────────────────────

    @Query("SELECT p FROM ParFixe p WHERE p.active = 'O' AND p.sensImput = 'C'")
    List<ParFixe> findGainsActifs();

    @Query("SELECT p FROM ParFixe p WHERE p.active = 'O' AND p.sensImput = 'D'")
    List<ParFixe> findRetenuesActives();

    // ── Impact retraite / impôt ───────────────────────────────────────────────

    @Query("SELECT p FROM ParFixe p WHERE p.rRetraite = 'O' AND p.active = 'O'")
    List<ParFixe> findCotisationsRetraite();

    @Query("SELECT p FROM ParFixe p WHERE p.impot = 'O' AND p.active = 'O'")
    List<ParFixe> findSoumisesImpot();

    // ── Référentiel trié ──────────────────────────────────────────────────────

    @Query("SELECT p FROM ParFixe p WHERE p.active = 'O' ORDER BY p.typePar, p.abrvFixe")
    List<ParFixe> findReferentielActif();

    // ── Statistiques ─────────────────────────────────────────────────────────

    @Query("SELECT p.typePar, COUNT(p) FROM ParFixe p WHERE p.active = 'O' GROUP BY p.typePar")
    List<Object[]> countActifsByType();
}