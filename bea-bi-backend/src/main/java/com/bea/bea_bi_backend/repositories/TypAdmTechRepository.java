package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.TypAdmTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypAdmTechRepository extends JpaRepository<TypAdmTech, String> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    List<TypAdmTech> findByTypAdmTec(String typAdmTec);

    List<TypAdmTech> findByCodCat(String codCat);

    List<TypAdmTech> findByCodCateg(String codCateg);

    List<TypAdmTech> findByCodGrad(String codGrad);

    List<TypAdmTech> findByNiveau(String niveau);

    // ── Droit véhicule ────────────────────────────────────────────────────────

    @Query("SELECT t FROM TypAdmTech t WHERE t.droitVehicul = 'O'")
    List<TypAdmTech> findAvecDroitVehicule();

    // ── Poste organique ───────────────────────────────────────────────────────

    @Query("SELECT t FROM TypAdmTech t WHERE t.postOrganique = :post")
    List<TypAdmTech> findByPosteOrganique(@Param("post") String postOrganique);

    // ── Référentiel complet trié ──────────────────────────────────────────────

    @Query("SELECT t FROM TypAdmTech t ORDER BY t.codCateg, t.codCat, t.admTech")
    List<TypAdmTech> findAllOrderedByCategorie();

    // ── Statistiques ─────────────────────────────────────────────────────────

    @Query("SELECT t.typAdmTec, COUNT(t) FROM TypAdmTech t GROUP BY t.typAdmTec")
    List<Object[]> countByType();
}