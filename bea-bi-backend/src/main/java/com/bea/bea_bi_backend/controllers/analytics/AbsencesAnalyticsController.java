package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.AbsencesAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analytics/absences")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AbsencesAnalyticsController {

    // Renommé pour éviter le conflit avec le paramètre @RequestParam "service"
    private final AbsencesAnalyticsService absencesAnalyticsService;

    @GetMapping("/par-motif")
    public ResponseEntity<List<VwAbsencesParMotif>> getAbsencesParMotif(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String motif) {
        return ResponseEntity.ok(absencesAnalyticsService.getAbsencesParMotif(service, annee, motif));
    }

    @GetMapping("/absenteisme-service")
    public ResponseEntity<List<VwAbsenteismeService>> getAbsenteismeService(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(absencesAnalyticsService.getAbsenteismeService(service, annee));
    }

    @GetMapping("/solde-conge-type")
    public ResponseEntity<List<VwSoldeCongeType>> getSoldeCongeType(
            @RequestParam(required = false) String typCng,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(absencesAnalyticsService.getSoldeCongeType(typCng, annee));
    }

    @GetMapping("/par-annee")
    public ResponseEntity<List<VwAbsencesParAnnee>> getAbsencesParAnnee(
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String typCng,
            @RequestParam(required = false) String service) {
        return ResponseEntity.ok(absencesAnalyticsService.getAbsencesParAnnee(annee, typCng, service));
    }

    @GetMapping("/top-absences-employe")
    public ResponseEntity<List<VwTopAbsencesEmploye>> getTopAbsencesEmploye(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee) {
        return ResponseEntity.ok(absencesAnalyticsService.getTopAbsencesEmploye(service, annee));
    }

    @GetMapping("/duree-moyenne-conge")
    public ResponseEntity<List<VwDureeMoyenneConge>> getDureeMoyenneConge(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String motif) {
        return ResponseEntity.ok(absencesAnalyticsService.getDureeMoyenneConge(service, annee, motif));
    }
}