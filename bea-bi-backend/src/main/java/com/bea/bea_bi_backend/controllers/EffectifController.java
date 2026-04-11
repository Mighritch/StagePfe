package com.bea.bea_bi_backend.controllers;

import com.bea.bea_bi_backend.dto.*;
import com.bea.bea_bi_backend.services.EffectifService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rh/effectif")
@RequiredArgsConstructor
public class EffectifController {

    private final EffectifService effectifService;

    @GetMapping("/par-service")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<EffectifParServiceDTO>> getEffectifParService(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(effectifService.getEffectifParService(codSoc));
    }

    @GetMapping("/par-grade")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<EffectifParGradeDTO>> getEffectifParGrade(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(effectifService.getEffectifParGrade(codSoc));
    }

    @GetMapping("/par-sexe")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<EffectifParSexeDTO>> getEffectifParSexe(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(effectifService.getEffectifParSexe(codSoc));
    }

    @GetMapping("/par-adm-tech")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<EffectifParAdmTechDTO>> getEffectifParAdmTech(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(effectifService.getEffectifParAdmTech(codSoc));
    }

    @GetMapping("/total")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<Long> getEffectifTotal(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(effectifService.getEffectifTotal(codSoc));
    }

    @GetMapping("/entrees")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<MouvementPersonnelDTO>> getEntrees(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(effectifService.getEntrees(codSoc, debut, fin));
    }

    @GetMapping("/departs")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<MouvementPersonnelDTO>> getDeparts(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(effectifService.getDeparts(codSoc, debut, fin));
    }

    @GetMapping("/taux-rotation")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<TauxRotationDTO> getTauxRotation(
            @RequestParam String codSoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(effectifService.getTauxRotation(codSoc, debut, fin));
    }

    @GetMapping("/handicapes")
    @PreAuthorize("hasAnyRole('RH', 'DIRECTION')")
    public ResponseEntity<List<HandicapeDTO>> getHandicapes(
            @RequestParam String codSoc) {
        return ResponseEntity.ok(effectifService.getHandicapes(codSoc));
    }
}