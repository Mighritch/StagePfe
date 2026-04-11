package com.bea.bea_bi_backend.repositories;

import com.bea.bea_bi_backend.entities.Service;
import com.bea.bea_bi_backend.entities.ServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, ServiceId> {

    // ── Filtres de base ───────────────────────────────────────────────────────

    @Query("SELECT s FROM Service s WHERE s.id.codSoc = :soc")
    List<Service> findByCodSoc(@Param("soc") String codSoc);

    @Query("SELECT s FROM Service s WHERE s.id.codSoc = :soc AND s.id.codServ = :serv")
    Optional<Service> findBySocieteAndCode(
            @Param("soc") String codSoc,
            @Param("serv") String codServ);

    @Query("SELECT s FROM Service s WHERE s.id.codSoc = :soc AND s.serCodServ = :parent")
    List<Service> findServicesByParent(
            @Param("soc") String codSoc,
            @Param("parent") String codServParent);

    @Query("SELECT s FROM Service s WHERE s.id.codSoc = :soc AND s.typeServ = :type")
    List<Service> findBySocieteAndType(
            @Param("soc") String codSoc,
            @Param("type") String typeServ);

    // ── Hiérarchie ────────────────────────────────────────────────────────────

    @Query("SELECT s FROM Service s WHERE s.id.codSoc = :soc AND s.serCodServ IS NULL")
    List<Service> findServicesRacinesBySociete(@Param("soc") String codSoc);

    // ── Référentiel trié ──────────────────────────────────────────────────────

    @Query("SELECT s FROM Service s WHERE s.id.codSoc = :soc ORDER BY s.ordre ASC NULLS LAST")
    List<Service> findByCodSocOrderByOrdre(@Param("soc") String codSoc);

    // ── Recherche par libellé ─────────────────────────────────────────────────

    @Query("SELECT s FROM Service s WHERE s.id.codSoc = :soc " +
            "AND UPPER(s.libServ) LIKE UPPER(CONCAT('%', :lib, '%'))")
    List<Service> findBySocieteAndLibelleContaining(
            @Param("soc") String codSoc,
            @Param("lib") String libServ);

    // ── Par région ────────────────────────────────────────────────────────────

    @Query("SELECT s FROM Service s WHERE s.id.codSoc = :soc AND s.codReg = :reg")
    List<Service> findBySocieteAndRegion(
            @Param("soc") String codSoc,
            @Param("reg") String codReg);
}