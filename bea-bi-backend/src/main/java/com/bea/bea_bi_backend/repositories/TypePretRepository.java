package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.TypePret;
import com.bea.bea_bi_backend.entities.TypePretId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TypePretRepository extends JpaRepository<TypePret, TypePretId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT t FROM TypePret t WHERE t.id.codSoc = :soc")
    List<TypePret> findByCodSoc(@Param("soc") String codSoc);

    @Query("SELECT t FROM TypePret t WHERE t.id.codSoc = :soc AND t.id.codGrpPret = :grp")
    List<TypePret> findBySocieteAndGroupe(
            @Param("soc") String codSoc,
            @Param("grp") String codGrpPret);

    @Query("SELECT t FROM TypePret t WHERE t.id.codSoc = :soc AND t.id.typPret = :typ")
    List<TypePret> findBySocieteAndType(
            @Param("soc") String codSoc,
            @Param("typ") String typPret);

    // ── Prêts logement ────────────────────────────────────────────────────────

    @Query("SELECT t FROM TypePret t WHERE t.id.codSoc = :soc AND t.prtLog = 'O'")
    List<TypePret> findPretsLogementBySociete(@Param("soc") String codSoc);

    // ── Renouvelables ─────────────────────────────────────────────────────────

    @Query("SELECT t FROM TypePret t WHERE t.id.codSoc = :soc AND t.renouv = 'O'")
    List<TypePret> findPretsRenouvelablesBySociete(@Param("soc") String codSoc);

    // ── Plafond / taux ────────────────────────────────────────────────────────

    @Query("SELECT t FROM TypePret t WHERE t.id.codSoc = :soc AND t.plafond >= :montant")
    List<TypePret> findByPlafondGreaterThanEqual(
            @Param("soc") String codSoc,
            @Param("montant") BigDecimal montant);

    // ── Référentiel trié ──────────────────────────────────────────────────────

    @Query("SELECT t FROM TypePret t WHERE t.id.codSoc = :soc " +
            "ORDER BY t.id.codGrpPret, t.id.typPret")
    List<TypePret> findReferentielBySociete(@Param("soc") String codSoc);

    // ── Statistiques ─────────────────────────────────────────────────────────

    @Query("SELECT t.id.codGrpPret, COUNT(t), AVG(t.tauxInt) FROM TypePret t " +
            "WHERE t.id.codSoc = :soc GROUP BY t.id.codGrpPret")
    List<Object[]> statsByGroupe(@Param("soc") String codSoc);
}