package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.CreditAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analytics/credit")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CreditAnalyticsController {

    private final CreditAnalyticsService service;

    @GetMapping("/encours-par-type-pret")
    public ResponseEntity<List<VwEncoursParTypePret>> getEncoursParTypePret(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        // 'service' fait maintenant référence au bean CreditAnalyticsService
        return ResponseEntity.ok(service.getEncoursParTypePret(typPret, nomService, annee));
    }

    @GetMapping("/taux-interet-pret")
    public ResponseEntity<List<VwTauxInteretPret>> getTauxInteretPret(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getTauxInteretPret(typPret, annee));
    }

    @GetMapping("/prets-par-service")
    public ResponseEntity<List<VwPretsParService>> getPretsParService(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getPretsParService(nomService, typPret, annee));
    }

    @GetMapping("/capital-restant-pret")
    public ResponseEntity<List<VwCapitalRestantPret>> getCapitalRestantPret(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getCapitalRestantPret(typPret, annee));
    }

    @GetMapping("/prets-par-duree")
    public ResponseEntity<List<VwPretsParDuree>> getPretsParDuree(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Integer duree) {
        return ResponseEntity.ok(service.getPretsParDuree(nomService, annee, duree));
    }

    @GetMapping("/prets-par-objet")
    public ResponseEntity<List<VwPretsParObjet>> getPretsParObjet(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String objet) {
        return ResponseEntity.ok(service.getPretsParObjet(nomService, annee, objet));
    }

    @GetMapping("/top-emprunteurs")
    public ResponseEntity<List<VwTopEmprunteurs>> getTopEmprunteurs(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(service.getTopEmprunteurs(nomService, annee));
    }
}