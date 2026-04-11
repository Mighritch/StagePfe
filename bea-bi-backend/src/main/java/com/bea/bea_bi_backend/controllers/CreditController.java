package com.bea.bea_bi_backend.controllers;

import com.bea.bea_bi_backend.dto.*;
import com.bea.bea_bi_backend.services.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @GetMapping("/prets-en-cours")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<PretEnCoursDTO>> getPretsEnCours(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(creditService.getPretsEnCours(codSoc));
    }

    @GetMapping("/prets-en-cours/par-service")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<PretEnCoursDTO>> getPretsEnCoursByService(
            @RequestParam String codSoc,
            @RequestParam String codServ) {
        return ResponseEntity.ok(creditService.getPretsEnCoursByService(codSoc, codServ));
    }

    @GetMapping("/stats/par-type")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<StatsCreditDTO>> getStatsParType(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(creditService.getStatsParType(codSoc));
    }

    @GetMapping("/stats/par-service")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<StatsCreditDTO>> getStatsParService(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(creditService.getStatsParService(codSoc));
    }

    @GetMapping("/stats/par-grade")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<StatsCreditDTO>> getStatsParGrade(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(creditService.getStatsParGrade(codSoc));
    }

    @GetMapping("/encours")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<BigDecimal> getEncours(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(creditService.getEncoursTotalBySociete(codSoc));
    }

    @GetMapping("/montant-accorde")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<BigDecimal> getMontantAccorde(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(creditService.getMontantAccorde(codSoc, debut, fin));
    }

    @GetMapping("/evolution")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<EvolutionPretDTO>> getEvolutionMensuelle(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(creditService.getEvolutionMensuelle(codSoc, debut, fin));
    }

    @GetMapping("/impayes")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<ImpayeDTO>> getImpayes(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(creditService.getImpayes(codSoc));
    }

    @GetMapping("/risque")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<PretEnCoursDTO>> getPretsARisque(
            @RequestParam String codSoc,
            @RequestParam(defaultValue = "3") int seuilMois) {
        return ResponseEntity.ok(creditService.getPretsARisque(codSoc, seuilMois));
    }

    @GetMapping("/echeancier")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<EcheancierDTO>> getEcheancier(
            @RequestParam String codSoc,
            @RequestParam String matPers,
            @RequestParam Long codPret) {
        return ResponseEntity.ok(creditService.getEcheancier(codSoc, matPers, codPret));
    }

    @GetMapping("/capital-restant")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<BigDecimal> getCapitalRestantParMois(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mois) {
        return ResponseEntity.ok(creditService.getCapitalRestantParMois(codSoc, mois));
    }
}