package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.TypBulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypBulletinRepository extends JpaRepository<TypBulletin, String> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    List<TypBulletin> findByTypBul(String typBul);

    List<TypBulletin> findBySolde(String solde);

    List<TypBulletin> findByRegimeTrav(String regimeTrav);

    // ── Bulletins non clôturés ────────────────────────────────────────────────

    @Query("SELECT t FROM TypBulletin t WHERE t.cloture IS NULL OR t.cloture = 'N'")
    List<TypBulletin> findBulletinsNonClotures();

    @Query("SELECT t FROM TypBulletin t WHERE t.cloture = 'O'")
    List<TypBulletin> findBulletinsClotures();

    // ── Bulletin de référence ─────────────────────────────────────────────────

    @Query("SELECT t FROM TypBulletin t WHERE t.codTypBulRef = :ref")
    List<TypBulletin> findByBulletinReference(@Param("ref") String codTypBulRef);

    // ── Référentiel trié par ordre ─────────────────────────────────────────────

    @Query("SELECT t FROM TypBulletin t ORDER BY t.ord ASC NULLS LAST")
    List<TypBulletin> findAllOrderByOrd();

    // ── Filtre reserve ────────────────────────────────────────────────────────

    @Query("SELECT t FROM TypBulletin t WHERE t.reserve IS NULL OR t.reserve = 'N'")
    List<TypBulletin> findBulletinsActifs();
}