package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AbsencesAnalyticsService {

    private final VwAbsencesParMotifRepository          absencesParMotifRepo;
    private final VwAbsenteismeServiceRepository        absenteismeServiceRepo;
    private final VwSoldeCongeTypeRepository            soldeCongeTypeRepo;
    private final VwAbsencesParAnneeRepository          absencesParAnneeRepo;
    private final VwTopAbsencesEmployeRepository        topAbsencesEmployeRepo;
    private final VwDureeMoyenneCongeRepository         dureeMoyenneCongeRepo;
    private final ExportService                         exportService;

    // ─────────────────────────────────────────────────────────────────────────
    //  DONNÉES
    // ─────────────────────────────────────────────────────────────────────────

    public List<VwAbsencesParMotif> getAbsencesParMotif(String service, Integer annee, String motif) {
        if (service != null || annee != null || motif != null) {
            return absencesParMotifRepo.findByFiltres(service, annee, motif);
        }
        return absencesParMotifRepo.findAll();
    }

    public List<VwAbsenteismeService> getAbsenteismeService(String service, Integer annee) {
        if (service != null || annee != null) {
            return absenteismeServiceRepo.findByFiltres(service, annee);
        }
        return absenteismeServiceRepo.findAll();
    }

    public List<VwSoldeCongeType> getSoldeCongeType(String typCng, Integer annee) {
        if (typCng != null || annee != null) {
            return soldeCongeTypeRepo.findByFiltres(typCng, annee);
        }
        return soldeCongeTypeRepo.findAll();
    }

    public List<VwAbsencesParAnnee> getAbsencesParAnnee(Integer annee, String typCng, String service) {
        if (annee != null || typCng != null || service != null) {
            return absencesParAnneeRepo.findByFiltres(annee, typCng, service);
        }
        return absencesParAnneeRepo.findAll();
    }

    public List<VwTopAbsencesEmploye> getTopAbsencesEmploye(String service, Integer annee) {
        if (service != null || annee != null) {
            return topAbsencesEmployeRepo.findByFiltres(service, annee);
        }
        return topAbsencesEmployeRepo.findAll();
    }

    public List<VwDureeMoyenneConge> getDureeMoyenneConge(String service, Integer annee, String motif) {
        if (service != null || annee != null || motif != null) {
            return dureeMoyenneCongeRepo.findByFiltres(service, annee, motif);
        }
        return dureeMoyenneCongeRepo.findAll();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT EXCEL
    // ─────────────────────────────────────────────────────────────────────────

    public byte[] exportAbsencesParMotifExcel(String service, Integer annee, String motif) {
        List<VwAbsencesParMotif> data = getAbsencesParMotif(service, annee, motif);
        return exportService.exportToExcel(
                data,
                "Absences par Motif",
                new String[]{"service", "annee", "motif", "nombreAbsences", "totalJours"},
                new String[]{"Service", "Année", "Motif", "Nombre d'absences", "Total jours"}
        );
    }

    public byte[] exportAbsenteismeServiceExcel(String service, Integer annee) {
        List<VwAbsenteismeService> data = getAbsenteismeService(service, annee);
        return exportService.exportToExcel(
                data,
                "Absentéisme par Service",
                new String[]{"service", "annee", "tauxAbsenteisme", "nombreAbsences", "totalJours"},
                new String[]{"Service", "Année", "Taux d'absentéisme (%)", "Nombre d'absences", "Total jours"}
        );
    }

    public byte[] exportSoldeCongeTypeExcel(String typCng, Integer annee) {
        List<VwSoldeCongeType> data = getSoldeCongeType(typCng, annee);
        return exportService.exportToExcel(
                data,
                "Solde Congé par Type",
                new String[]{"typCng", "annee", "soldeMoyen", "soldeTotal", "nombreEmployes"},
                new String[]{"Type de congé", "Année", "Solde moyen", "Solde total", "Nb employés"}
        );
    }

    public byte[] exportAbsencesParAnneeExcel(Integer annee, String typCng, String service) {
        List<VwAbsencesParAnnee> data = getAbsencesParAnnee(annee, typCng, service);
        return exportService.exportToExcel(
                data,
                "Absences par Année",
                new String[]{"annee", "typCng", "service", "nombreAbsences", "totalJours"},
                new String[]{"Année", "Type de congé", "Service", "Nombre d'absences", "Total jours"}
        );
    }

    public byte[] exportTopAbsencesEmployeExcel(String service, Integer annee) {
        List<VwTopAbsencesEmploye> data = getTopAbsencesEmploye(service, annee);
        return exportService.exportToExcel(
                data,
                "Top Absences Employé",
                new String[]{"nomEmploye", "service", "annee", "nombreAbsences", "totalJours"},
                new String[]{"Employé", "Service", "Année", "Nombre d'absences", "Total jours"}
        );
    }

    public byte[] exportDureeMoyenneCongeExcel(String service, Integer annee, String motif) {
        List<VwDureeMoyenneConge> data = getDureeMoyenneConge(service, annee, motif);
        return exportService.exportToExcel(
                data,
                "Durée Moyenne Congé",
                new String[]{"service", "annee", "motif", "dureeMoyenne", "nombreConges"},
                new String[]{"Service", "Année", "Motif", "Durée moyenne (j)", "Nombre de congés"}
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT PDF
    // ─────────────────────────────────────────────────────────────────────────

    public byte[] exportAbsencesParMotifPdf(String service, Integer annee, String motif) {
        List<VwAbsencesParMotif> data = getAbsencesParMotif(service, annee, motif);
        Map<String, Object> params = buildParams("Absences par Motif", service, annee);
        if (motif != null) params.put("motif", motif);
        return exportService.exportToPdf(data, "absences_par_motif", params);
    }

    public byte[] exportAbsenteismeServicePdf(String service, Integer annee) {
        List<VwAbsenteismeService> data = getAbsenteismeService(service, annee);
        return exportService.exportToPdf(data, "absenteisme_service", buildParams("Absentéisme par Service", service, annee));
    }

    public byte[] exportSoldeCongeTypePdf(String typCng, Integer annee) {
        List<VwSoldeCongeType> data = getSoldeCongeType(typCng, annee);
        Map<String, Object> params = buildParams("Solde Congé par Type", null, annee);
        if (typCng != null) params.put("typCng", typCng);
        return exportService.exportToPdf(data, "solde_conge_type", params);
    }

    public byte[] exportAbsencesParAnneePdf(Integer annee, String typCng, String service) {
        List<VwAbsencesParAnnee> data = getAbsencesParAnnee(annee, typCng, service);
        Map<String, Object> params = buildParams("Absences par Année", service, annee);
        if (typCng != null) params.put("typCng", typCng);
        return exportService.exportToPdf(data, "absences_par_annee", params);
    }

    public byte[] exportTopAbsencesEmployePdf(String service, Integer annee) {
        List<VwTopAbsencesEmploye> data = getTopAbsencesEmploye(service, annee);
        return exportService.exportToPdf(data, "top_absences_employe", buildParams("Top Absences Employé", service, annee));
    }

    public byte[] exportDureeMoyenneCongePdf(String service, Integer annee, String motif) {
        List<VwDureeMoyenneConge> data = getDureeMoyenneConge(service, annee, motif);
        Map<String, Object> params = buildParams("Durée Moyenne Congé", service, annee);
        if (motif != null) params.put("motif", motif);
        return exportService.exportToPdf(data, "duree_moyenne_conge", params);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  UTILITAIRE PRIVÉ
    // ─────────────────────────────────────────────────────────────────────────

    private Map<String, Object> buildParams(String title, String service, Integer annee) {
        Map<String, Object> params = new java.util.HashMap<>();
        params.put("REPORT_TITLE", title);
        if (service != null) params.put("service", service);
        if (annee  != null) params.put("annee",   annee);
        return params;
    }
}