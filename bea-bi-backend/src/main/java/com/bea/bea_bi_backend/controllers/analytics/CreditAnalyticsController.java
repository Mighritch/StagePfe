package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.CreditAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/credit")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CreditAnalyticsController {

    private final CreditAnalyticsService service;

    // ─────────────────────────────────────────────────────────────────────────
    //  DONNÉES
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/encours-par-type-pret")
    public ResponseEntity<List<VwEncoursParTypePret>> getEncoursParTypePret(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
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

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT EXCEL
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/encours-par-type-pret/export/excel")
    public ResponseEntity<byte[]> exportEncoursParTypePretExcel(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportEncoursParTypePretExcel(typPret, nomService, annee), "encours_par_type_pret.xlsx");
    }

    @GetMapping("/taux-interet-pret/export/excel")
    public ResponseEntity<byte[]> exportTauxInteretPretExcel(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportTauxInteretPretExcel(typPret, annee), "taux_interet_pret.xlsx");
    }

    @GetMapping("/prets-par-service/export/excel")
    public ResponseEntity<byte[]> exportPretsParServiceExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportPretsParServiceExcel(nomService, typPret, annee), "prets_par_service.xlsx");
    }

    @GetMapping("/capital-restant-pret/export/excel")
    public ResponseEntity<byte[]> exportCapitalRestantPretExcel(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportCapitalRestantPretExcel(typPret, annee), "capital_restant_pret.xlsx");
    }

    @GetMapping("/prets-par-duree/export/excel")
    public ResponseEntity<byte[]> exportPretsParDureeExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Integer duree) {
        return buildExcelResponse(service.exportPretsParDureeExcel(nomService, annee, duree), "prets_par_duree.xlsx");
    }

    @GetMapping("/prets-par-objet/export/excel")
    public ResponseEntity<byte[]> exportPretsParObjetExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String objet) {
        return buildExcelResponse(service.exportPretsParObjetExcel(nomService, annee, objet), "prets_par_objet.xlsx");
    }

    @GetMapping("/top-emprunteurs/export/excel")
    public ResponseEntity<byte[]> exportTopEmprunteursExcel(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildExcelResponse(service.exportTopEmprunteursExcel(nomService, annee), "top_emprunteurs.xlsx");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT PDF
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/encours-par-type-pret/export/pdf")
    public ResponseEntity<byte[]> exportEncoursParTypePretPdf(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportEncoursParTypePretPdf(typPret, nomService, annee), "encours_par_type_pret.pdf");
    }

    @GetMapping("/taux-interet-pret/export/pdf")
    public ResponseEntity<byte[]> exportTauxInteretPretPdf(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportTauxInteretPretPdf(typPret, annee), "taux_interet_pret.pdf");
    }

    @GetMapping("/prets-par-service/export/pdf")
    public ResponseEntity<byte[]> exportPretsParServicePdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportPretsParServicePdf(nomService, typPret, annee), "prets_par_service.pdf");
    }

    @GetMapping("/capital-restant-pret/export/pdf")
    public ResponseEntity<byte[]> exportCapitalRestantPretPdf(
            @RequestParam(required = false) String typPret,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportCapitalRestantPretPdf(typPret, annee), "capital_restant_pret.pdf");
    }

    @GetMapping("/prets-par-duree/export/pdf")
    public ResponseEntity<byte[]> exportPretsParDureePdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Integer duree) {
        return buildPdfResponse(service.exportPretsParDureePdf(nomService, annee, duree), "prets_par_duree.pdf");
    }

    @GetMapping("/prets-par-objet/export/pdf")
    public ResponseEntity<byte[]> exportPretsParObjetPdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String objet) {
        return buildPdfResponse(service.exportPretsParObjetPdf(nomService, annee, objet), "prets_par_objet.pdf");
    }

    @GetMapping("/top-emprunteurs/export/pdf")
    public ResponseEntity<byte[]> exportTopEmprunteursPdf(
            @RequestParam(required = false, name = "service") String nomService,
            @RequestParam(required = false) Integer annee) {
        return buildPdfResponse(service.exportTopEmprunteursPdf(nomService, annee), "top_emprunteurs.pdf");
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