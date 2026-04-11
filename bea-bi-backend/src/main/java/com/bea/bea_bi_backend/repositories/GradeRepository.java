package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.Grade;
import com.bea.bea_bi_backend.entities.GradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, GradeId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT g FROM Grade g WHERE g.id.codCateg = :categ")
    List<Grade> findByCategorie(@Param("categ") String codCateg);

    @Query("SELECT g FROM Grade g WHERE g.id.codCat = :cat")
    List<Grade> findByCategorieProfessionnelle(@Param("cat") String codCat);

    @Query("SELECT g FROM Grade g WHERE g.id.codCateg = :categ AND g.id.codCat = :cat")
    List<Grade> findByCategorieAndCategorieProfessionnelle(
            @Param("categ") String codCateg,
            @Param("cat") String codCat);

    List<Grade> findByCodSoc(String codSoc);

    List<Grade> findByTypCat(String typCat);

    // ── Recherche par libellé ─────────────────────────────────────────────────

    @Query("SELECT g FROM Grade g WHERE UPPER(g.libGrad) LIKE UPPER(CONCAT('%', :lib, '%'))")
    List<Grade> findByLibelleContaining(@Param("lib") String libGrad);

    // ── Liste pour référentiel ────────────────────────────────────────────────

    @Query("SELECT g.id.codGrad, g.libGrad, g.id.codCateg, g.id.codCat FROM Grade g " +
            "WHERE g.codSoc = :soc ORDER BY g.id.codCateg, g.id.codCat, g.id.codGrad")
    List<Object[]> findReferentielGradesBySociete(@Param("soc") String codSoc);
}