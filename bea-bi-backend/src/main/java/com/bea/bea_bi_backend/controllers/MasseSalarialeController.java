package com.bea.bea_bi_backend.controllers;

import com.bea.bea_bi_backend.dto.*;
import com.bea.bea_bi_backend.services.MasseSalarialeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/salaire")
@RequiredArgsConstructor
public class MasseSalarialeController {

    private final MasseSalarialeService masseSalarialeService;

    @GetMapping("/par-mois")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<MasseSalarialeParMoisDTO>> getMasseSalarialeParMois(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(
                masseSalarialeService.getMasseSalarialeParMois(codSoc, debut, fin));
    }

    @GetMapping("/par-service")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<MasseSalarialeParServiceDTO>> getMasseSalarialeParService(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(
                masseSalarialeService.getMasseSalarialeParService(codSoc, debut, fin));
    }

    @GetMapping("/par-rubrique")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<RubriqueDTO>> getMasseSalarialeParRubrique(
            @RequestParam String codSoc,
            @RequestParam String typePar,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(
                masseSalarialeService.getMasseSalarialeParRubrique(codSoc, typePar, debut, fin));
    }

    @GetMapping("/gains")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<RubriqueDTO>> getGainsActifs(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(masseSalarialeService.getGainsActifs(codSoc));
    }

    @GetMapping("/retenues")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<List<RubriqueDTO>> getRetenuesActives(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(masseSalarialeService.getRetenuesActives(codSoc));
    }

    @GetMapping("/presence/par-service")
    @PreAuthorize("hasAnyRole('RH', 'FINANCE', 'DIRECTION')")
    public ResponseEntity<List<PresenceDTO>> getPresenceParService(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(
                masseSalarialeService.getPresenceParService(codSoc, debut, fin));
    }

    @GetMapping("/presence/par-grade")
    @PreAuthorize("hasAnyRole('RH', 'FINANCE', 'DIRECTION')")
    public ResponseEntity<List<PresenceDTO>> getPresenceParGrade(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(
                masseSalarialeService.getPresenceParGrade(codSoc, debut, fin));
    }

    @GetMapping("/total")
    @PreAuthorize("hasAnyRole('FINANCE', 'DIRECTION')")
    public ResponseEntity<BigDecimal> getMasseSalarialeTotale(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(
                masseSalarialeService.getMasseSalarialeTotale(codSoc, debut, fin));
    }
}