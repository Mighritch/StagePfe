package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.EffectifAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analytics/effectif")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EffectifAnalyticsController {

    private final EffectifAnalyticsService service;

    @GetMapping("/par-service")
    public ResponseEntity<List<VwEffectifParService>> getEffectifParService(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getEffectifParService(nomService, grade, annee));
    }

    @GetMapping("/par-grade")
    public ResponseEntity<List<VwEffectifParGrade>> getEffectifParGrade(
            @RequestParam(required = false) String grade,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getEffectifParGrade(grade, nomService, annee));
    }

    @GetMapping("/par-admtech")
    public ResponseEntity<List<VwEffectifParAdmtech>> getEffectifParAdmtech(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getEffectifParAdmtech(nomService, annee));
    }

    @GetMapping("/par-sexe")
    public ResponseEntity<List<VwEffectifParSexe>> getEffectifParSexe(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getEffectifParSexe(nomService, grade, annee));
    }

    @GetMapping("/evolution")
    public ResponseEntity<List<VwEffectifEvolution>> getEffectifEvolution(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getEffectifEvolution(nomService, grade, annee));
    }

    @GetMapping("/service-grade")
    public ResponseEntity<List<VwEffectifServiceGrade>> getEffectifServiceGrade(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getEffectifServiceGrade(nomService, grade, annee));
    }

    @GetMapping("/par-affectation")
    public ResponseEntity<List<VwEffectifParAffectation>> getEffectifParAffectation(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getEffectifParAffectation(nomService, annee));
    }
}