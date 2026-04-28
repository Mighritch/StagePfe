package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.ChargesAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analytics/charges")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChargesAnalyticsController {

    private final ChargesAnalyticsService service;

    @GetMapping("/masse-salariale-mois")
    public ResponseEntity<List<VwMasseSalarialeMois>> getMasseSalarialeMois(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Integer mois) {
        return ResponseEntity.ok(service.getMasseSalarialeMois(nomService, annee, mois));
    }

    @GetMapping("/par-type-bulletin")
    public ResponseEntity<List<VwChargesParTypeBulletin>> getChargesParTypeBulletin(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getChargesParTypeBulletin(nomService, annee));
    }

    @GetMapping("/masse-salariale-service")
    public ResponseEntity<List<VwMasseSalarialeService>> getMasseSalarialeService(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getMasseSalarialeService(nomService, annee));
    }

    @GetMapping("/salaire-par-grade")
    public ResponseEntity<List<VwSalaireParGrade>> getSalaireParGrade(
            @RequestParam(required = false) String grade,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getSalaireParGrade(grade, nomService, annee));
    }

    @GetMapping("/evolution-masse-salariale")
    public ResponseEntity<List<VwEvolutionMasseSalariale>> getEvolutionMasseSalariale(
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getEvolutionMasseSalariale(annee));
    }

    @GetMapping("/taux-charge-service")
    public ResponseEntity<List<VwTauxChargeService>> getTauxChargeService(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getTauxChargeService(nomService, annee));
    }
}