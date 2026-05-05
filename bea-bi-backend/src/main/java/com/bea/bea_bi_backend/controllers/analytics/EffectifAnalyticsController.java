package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.EffectifAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/effectif")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EffectifAnalyticsController {

    private final EffectifAnalyticsService service;

    // ─────────────────────────────────────────────────────────────────────────
    //  DONNÉES
    // ─────────────────────────────────────────────────────────────────────────

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

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT EXCEL
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/par-service/export/excel")
    public ResponseEntity<byte[]> exportEffectifParServiceExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEffectifParServiceExcel(nomService, grade, annee), "effectif_par_service.xlsx");
    }

    @GetMapping("/par-grade/export/excel")
    public ResponseEntity<byte[]> exportEffectifParGradeExcel(
            @RequestParam(required = false) String grade,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEffectifParGradeExcel(grade, nomService, annee), "effectif_par_grade.xlsx");
    }

    @GetMapping("/par-admtech/export/excel")
    public ResponseEntity<byte[]> exportEffectifParAdmtechExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEffectifParAdmtechExcel(nomService, annee), "effectif_par_admtech.xlsx");
    }

    @GetMapping("/par-sexe/export/excel")
    public ResponseEntity<byte[]> exportEffectifParSexeExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEffectifParSexeExcel(nomService, grade, annee), "effectif_par_sexe.xlsx");
    }

    @GetMapping("/evolution/export/excel")
    public ResponseEntity<byte[]> exportEffectifEvolutionExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEffectifEvolutionExcel(nomService, grade, annee), "effectif_evolution.xlsx");
    }

    @GetMapping("/service-grade/export/excel")
    public ResponseEntity<byte[]> exportEffectifServiceGradeExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEffectifServiceGradeExcel(nomService, grade, annee), "effectif_service_grade.xlsx");
    }

    @GetMapping("/par-affectation/export/excel")
    public ResponseEntity<byte[]> exportEffectifParAffectationExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEffectifParAffectationExcel(nomService, annee), "effectif_par_affectation.xlsx");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT PDF
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/par-service/export/pdf")
    public ResponseEntity<byte[]> exportEffectifParServicePdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEffectifParServicePdf(nomService, grade, annee), "effectif_par_service.pdf");
    }

    @GetMapping("/par-grade/export/pdf")
    public ResponseEntity<byte[]> exportEffectifParGradePdf(
            @RequestParam(required = false) String grade,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEffectifParGradePdf(grade, nomService, annee), "effectif_par_grade.pdf");
    }

    @GetMapping("/par-admtech/export/pdf")
    public ResponseEntity<byte[]> exportEffectifParAdmtechPdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEffectifParAdmtechPdf(nomService, annee), "effectif_par_admtech.pdf");
    }

    @GetMapping("/par-sexe/export/pdf")
    public ResponseEntity<byte[]> exportEffectifParSexePdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEffectifParSexePdf(nomService, grade, annee), "effectif_par_sexe.pdf");
    }

    @GetMapping("/evolution/export/pdf")
    public ResponseEntity<byte[]> exportEffectifEvolutionPdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEffectifEvolutionPdf(nomService, grade, annee), "effectif_evolution.pdf");
    }

    @GetMapping("/service-grade/export/pdf")
    public ResponseEntity<byte[]> exportEffectifServiceGradePdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEffectifServiceGradePdf(nomService, grade, annee), "effectif_service_grade.pdf");
    }

    @GetMapping("/par-affectation/export/pdf")
    public ResponseEntity<byte[]> exportEffectifParAffectationPdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEffectifParAffectationPdf(nomService, annee), "effectif_par_affectation.pdf");
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