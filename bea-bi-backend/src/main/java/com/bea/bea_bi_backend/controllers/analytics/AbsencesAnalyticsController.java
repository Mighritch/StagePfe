package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.AbsencesAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/absences")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AbsencesAnalyticsController {

    private final AbsencesAnalyticsService absencesAnalyticsService;

    // ─────────────────────────────────────────────────────────────────────────
    //  DONNÉES
    // ─────────────────────────────────────────────────────────────────────────

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

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT EXCEL
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/par-motif/export/excel")
    public ResponseEntity<byte[]> exportAbsencesParMotifExcel(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String motif) {
        byte[] bytes = absencesAnalyticsService.exportAbsencesParMotifExcel(service, annee, motif);
        return buildExcelResponse(bytes, "absences_par_motif.xlsx");
    }

    @GetMapping("/absenteisme-service/export/excel")
    public ResponseEntity<byte[]> exportAbsenteismeServiceExcel(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee) {
        byte[] bytes = absencesAnalyticsService.exportAbsenteismeServiceExcel(service, annee);
        return buildExcelResponse(bytes, "absenteisme_service.xlsx");
    }

    @GetMapping("/solde-conge-type/export/excel")
    public ResponseEntity<byte[]> exportSoldeCongeTypeExcel(
            @RequestParam(required = false) String typCng,
            @RequestParam(required = false) Integer annee) {
        byte[] bytes = absencesAnalyticsService.exportSoldeCongeTypeExcel(typCng, annee);
        return buildExcelResponse(bytes, "solde_conge_type.xlsx");
    }

    @GetMapping("/par-annee/export/excel")
    public ResponseEntity<byte[]> exportAbsencesParAnneeExcel(
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String typCng,
            @RequestParam(required = false) String service) {
        byte[] bytes = absencesAnalyticsService.exportAbsencesParAnneeExcel(annee, typCng, service);
        return buildExcelResponse(bytes, "absences_par_annee.xlsx");
    }

    @GetMapping("/top-absences-employe/export/excel")
    public ResponseEntity<byte[]> exportTopAbsencesEmployeExcel(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee) {
        byte[] bytes = absencesAnalyticsService.exportTopAbsencesEmployeExcel(service, annee);
        return buildExcelResponse(bytes, "top_absences_employe.xlsx");
    }

    @GetMapping("/duree-moyenne-conge/export/excel")
    public ResponseEntity<byte[]> exportDureeMoyenneCongeExcel(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String motif) {
        byte[] bytes = absencesAnalyticsService.exportDureeMoyenneCongeExcel(service, annee, motif);
        return buildExcelResponse(bytes, "duree_moyenne_conge.xlsx");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT PDF
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/par-motif/export/pdf")
    public ResponseEntity<byte[]> exportAbsencesParMotifPdf(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String motif) {
        byte[] bytes = absencesAnalyticsService.exportAbsencesParMotifPdf(service, annee, motif);
        return buildPdfResponse(bytes, "absences_par_motif.pdf");
    }

    @GetMapping("/absenteisme-service/export/pdf")
    public ResponseEntity<byte[]> exportAbsenteismeServicePdf(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee) {
        byte[] bytes = absencesAnalyticsService.exportAbsenteismeServicePdf(service, annee);
        return buildPdfResponse(bytes, "absenteisme_service.pdf");
    }

    @GetMapping("/solde-conge-type/export/pdf")
    public ResponseEntity<byte[]> exportSoldeCongeTypePdf(
            @RequestParam(required = false) String typCng,
            @RequestParam(required = false) Integer annee) {
        byte[] bytes = absencesAnalyticsService.exportSoldeCongeTypePdf(typCng, annee);
        return buildPdfResponse(bytes, "solde_conge_type.pdf");
    }

    @GetMapping("/par-annee/export/pdf")
    public ResponseEntity<byte[]> exportAbsencesParAnneePdf(
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String typCng,
            @RequestParam(required = false) String service) {
        byte[] bytes = absencesAnalyticsService.exportAbsencesParAnneePdf(annee, typCng, service);
        return buildPdfResponse(bytes, "absences_par_annee.pdf");
    }

    @GetMapping("/top-absences-employe/export/pdf")
    public ResponseEntity<byte[]> exportTopAbsencesEmployePdf(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee) {
        byte[] bytes = absencesAnalyticsService.exportTopAbsencesEmployePdf(service, annee);
        return buildPdfResponse(bytes, "top_absences_employe.pdf");
    }

    @GetMapping("/duree-moyenne-conge/export/pdf")
    public ResponseEntity<byte[]> exportDureeMoyenneCongePdf(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String motif) {
        byte[] bytes = absencesAnalyticsService.exportDureeMoyenneCongePdf(service, annee, motif);
        return buildPdfResponse(bytes, "duree_moyenne_conge.pdf");
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