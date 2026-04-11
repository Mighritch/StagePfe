package com.bea.bea_bi_backend.controllers;

import com.bea.bea_bi_backend.dto.*;
import com.bea.bea_bi_backend.services.AbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/absences")
@RequiredArgsConstructor
public class AbsenceController {

    private final AbsenceService absenceService;

    @GetMapping("/par-motif")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<AbsenceParMotifDTO>> getAbsencesParMotif(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(absenceService.getAbsencesParMotif(codSoc, debut, fin));
    }

    @GetMapping("/par-service")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<AbsenceParServiceDTO>> getAbsencesParService(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(absenceService.getAbsencesParService(codSoc, debut, fin));
    }

    @GetMapping("/par-annee")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<AbsenceParMotifDTO>> getAbsencesParAnnee(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(absenceService.getAbsencesParAnnee(codSoc));
    }

    @GetMapping("/en-attente")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<SoldeCongeDTO>> getDemandesEnAttente(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(absenceService.getDemandesEnAttente(codSoc));
    }

    @GetMapping("/historique")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<SoldeCongeDTO>> getHistoriqueParMatricule(
            @RequestParam String codSoc,
            @RequestParam String matPers) {
        return ResponseEntity.ok(absenceService.getDemandesByMatricule(codSoc, matPers));
    }

    @GetMapping("/soldes")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<SoldeCongeDTO>> getSoldesParType(
            @RequestParam String codSoc,
            @RequestParam Integer annee) {
        return ResponseEntity.ok(absenceService.getSoldesParType(codSoc, annee));
    }

    @GetMapping("/soldes/positifs")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<SoldeCongeDTO>> getSoldesPositifs(
            @RequestParam String codSoc,
            @RequestParam Integer annee) {
        return ResponseEntity.ok(absenceService.getSoldesPositifs(codSoc, annee));
    }

    @GetMapping("/evolution")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<SoldeCongeDTO>> getEvolutionConges(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(absenceService.getEvolutionConges(codSoc));
    }

    @GetMapping("/non-justifiees")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<Integer> getAbsencesNonJustifiees(
            @RequestParam String codSoc,
            @RequestParam Integer annee) {
        return ResponseEntity.ok(absenceService.getAbsencesNonJustifiees(codSoc, annee));
    }
}