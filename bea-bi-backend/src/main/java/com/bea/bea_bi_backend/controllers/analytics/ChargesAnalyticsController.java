package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.ChargesAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/charges")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChargesAnalyticsController {

    private final ChargesAnalyticsService service;

    // ─────────────────────────────────────────────────────────────────────────
    //  DONNÉES
    // ─────────────────────────────────────────────────────────────────────────

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

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT EXCEL
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/masse-salariale-mois/export/excel")
    public ResponseEntity<byte[]> exportMasseSalarialeMoisExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Integer mois) {
        return buildExcelResponse(service.exportMasseSalarialeMoisExcel(nomService, annee, mois), "masse_salariale_mois.xlsx");
    }

    @GetMapping("/par-type-bulletin/export/excel")
    public ResponseEntity<byte[]> exportChargesParTypeBulletinExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportChargesParTypeBulletinExcel(nomService, annee), "charges_par_type_bulletin.xlsx");
    }

    @GetMapping("/masse-salariale-service/export/excel")
    public ResponseEntity<byte[]> exportMasseSalarialeServiceExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportMasseSalarialeServiceExcel(nomService, annee), "masse_salariale_service.xlsx");
    }

    @GetMapping("/salaire-par-grade/export/excel")
    public ResponseEntity<byte[]> exportSalaireParGradeExcel(
            @RequestParam(required = false) String grade,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportSalaireParGradeExcel(grade, nomService, annee), "salaire_par_grade.xlsx");
    }

    @GetMapping("/evolution-masse-salariale/export/excel")
    public ResponseEntity<byte[]> exportEvolutionMasseSalarialeExcel(
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEvolutionMasseSalarialeExcel(annee), "evolution_masse_salariale.xlsx");
    }

    @GetMapping("/taux-charge-service/export/excel")
    public ResponseEntity<byte[]> exportTauxChargeServiceExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportTauxChargeServiceExcel(nomService, annee), "taux_charge_service.xlsx");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT PDF
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/masse-salariale-mois/export/pdf")
    public ResponseEntity<byte[]> exportMasseSalarialeMoisPdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Integer mois) {
        return buildPdfResponse(service.exportMasseSalarialeMoisPdf(nomService, annee, mois), "masse_salariale_mois.pdf");
    }

    @GetMapping("/par-type-bulletin/export/pdf")
    public ResponseEntity<byte[]> exportChargesParTypeBulletinPdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportChargesParTypeBulletinPdf(nomService, annee), "charges_par_type_bulletin.pdf");
    }

    @GetMapping("/masse-salariale-service/export/pdf")
    public ResponseEntity<byte[]> exportMasseSalarialeServicePdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportMasseSalarialeServicePdf(nomService, annee), "masse_salariale_service.pdf");
    }

    @GetMapping("/salaire-par-grade/export/pdf")
    public ResponseEntity<byte[]> exportSalaireParGradePdf(
            @RequestParam(required = false) String grade,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportSalaireParGradePdf(grade, nomService, annee), "salaire_par_grade.pdf");
    }

    @GetMapping("/evolution-masse-salariale/export/pdf")
    public ResponseEntity<byte[]> exportEvolutionMasseSalarialePdf(
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEvolutionMasseSalarialePdf(annee), "evolution_masse_salariale.pdf");
    }

    @GetMapping("/taux-charge-service/export/pdf")
    public ResponseEntity<byte[]> exportTauxChargeServicePdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportTauxChargeServicePdf(nomService, annee), "taux_charge_service.pdf");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  HELPERS
    // ─────────────────────────────────────────────────────────────────────────

    private ResponseEntity<byte[]> buildExcelResponse(byte[] bytes, String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(filename).build().toString())
                .body(bytes);
    }

    private ResponseEntity<byte[]> buildPdfResponse(byte[] bytes, String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(filename).build().toString())
                .body(bytes);
    }
}