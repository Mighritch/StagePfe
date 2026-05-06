package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EffectifAnalyticsService {

    private final VwEffectifParServiceRepository      effectifParServiceRepo;
    private final VwEffectifParGradeRepository        effectifParGradeRepo;
    private final VwEffectifParAdmtechRepository      effectifParAdmtechRepo;
    private final VwEffectifParSexeRepository         effectifParSexeRepo;
    private final VwEffectifEvolutionRepository       effectifEvolutionRepo;
    private final VwEffectifServiceGradeRepository    effectifServiceGradeRepo;
    private final VwEffectifParAffectationRepository  effectifParAffectationRepo;
    private final ExportService                       exportService;

    // ─────────────────────────────────────────────────────────────────────────
    //  DONNÉES
    // ─────────────────────────────────────────────────────────────────────────

    public List<VwEffectifParService> getEffectifParService(String service, String grade, Integer annee) {
        if (service != null || grade != null || annee != null) {
            return effectifParServiceRepo.findByFiltres(service, grade, annee);
        }
        return effectifParServiceRepo.findAll();
    }

    public List<VwEffectifParGrade> getEffectifParGrade(String grade, String service, Integer annee) {
        if (grade != null || service != null || annee != null) {
            return effectifParGradeRepo.findByFiltres(grade, service, annee);
        }
        return effectifParGradeRepo.findAll();
    }

    public List<VwEffectifParAdmtech> getEffectifParAdmtech(String service, Integer annee) {
        if (service != null || annee != null) {
            return effectifParAdmtechRepo.findByFiltres(service, annee);
        }
        return effectifParAdmtechRepo.findAll();
    }

    public List<VwEffectifParSexe> getEffectifParSexe(String service, String grade, Integer annee) {
        if (service != null || grade != null || annee != null) {
            return effectifParSexeRepo.findByFiltres(service, grade, annee);
        }
        return effectifParSexeRepo.findAll();
    }

    public List<VwEffectifEvolution> getEffectifEvolution(String service, String grade, Integer annee) {
        if (service != null || grade != null || annee != null) {
            return effectifEvolutionRepo.findByFiltres(service, grade, annee);
        }
        return effectifEvolutionRepo.findAll();
    }

    public List<VwEffectifServiceGrade> getEffectifServiceGrade(String service, String grade, Integer annee) {
        if (service != null || grade != null || annee != null) {
            return effectifServiceGradeRepo.findByFiltres(service, grade, annee);
        }
        return effectifServiceGradeRepo.findAll();
    }

    public List<VwEffectifParAffectation> getEffectifParAffectation(String service, Integer annee) {
        if (service != null || annee != null) {
            return effectifParAffectationRepo.findByFiltres(service, annee);
        }
        return effectifParAffectationRepo.findAll();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT EXCEL
    // ─────────────────────────────────────────────────────────────────────────

    public byte[] exportEffectifParServiceExcel(String service, String grade, Integer annee) {
        List<VwEffectifParService> data = getEffectifParService(service, grade, annee);
        return exportService.exportToExcel(
                data,
                "Effectif par Service",
                new String[]{"service", "grade", "annee", "effectif"},
                new String[]{"Service", "Grade", "Année", "Effectif"}
        );
    }

    public byte[] exportEffectifParGradeExcel(String grade, String service, Integer annee) {
        List<VwEffectifParGrade> data = getEffectifParGrade(grade, service, annee);
        return exportService.exportToExcel(
                data,
                "Effectif par Grade",
                new String[]{"grade", "service", "annee", "effectif", "pourcentage"},
                new String[]{"Grade", "Service", "Année", "Effectif", "Pourcentage (%)"}
        );
    }

    public byte[] exportEffectifParAdmtechExcel(String service, Integer annee) {
        List<VwEffectifParAdmtech> data = getEffectifParAdmtech(service, annee);
        return exportService.exportToExcel(
                data,
                "Effectif Administratif / Technique",
                new String[]{"service", "annee", "categorie", "effectif", "pourcentage"},
                new String[]{"Service", "Année", "Catégorie", "Effectif", "Pourcentage (%)"}
        );
    }

    public byte[] exportEffectifParSexeExcel(String service, String grade, Integer annee) {
        List<VwEffectifParSexe> data = getEffectifParSexe(service, grade, annee);
        return exportService.exportToExcel(
                data,
                "Effectif par Sexe",
                new String[]{"service", "grade", "annee", "sexe", "effectif", "pourcentage"},
                new String[]{"Service", "Grade", "Année", "Sexe", "Effectif", "Pourcentage (%)"}
        );
    }

    public byte[] exportEffectifEvolutionExcel(String service, String grade, Integer annee) {
        List<VwEffectifEvolution> data = getEffectifEvolution(service, grade, annee);
        return exportService.exportToExcel(
                data,
                "Évolution de l'Effectif",
                new String[]{"service", "grade", "annee", "effectif", "evolution", "tauxEvolution"},
                new String[]{"Service", "Grade", "Année", "Effectif", "Évolution", "Taux d'évolution (%)"}
        );
    }

    public byte[] exportEffectifServiceGradeExcel(String service, String grade, Integer annee) {
        List<VwEffectifServiceGrade> data = getEffectifServiceGrade(service, grade, annee);
        return exportService.exportToExcel(
                data,
                "Effectif Service / Grade",
                new String[]{"service", "grade", "annee", "effectif"},
                new String[]{"Service", "Grade", "Année", "Effectif"}
        );
    }

    public byte[] exportEffectifParAffectationExcel(String service, Integer annee) {
        List<VwEffectifParAffectation> data = getEffectifParAffectation(service, annee);
        return exportService.exportToExcel(
                data,
                "Effectif par Affectation",
                new String[]{"service", "annee", "affectation", "effectif", "pourcentage"},
                new String[]{"Service", "Année", "Affectation", "Effectif", "Pourcentage (%)"}
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT PDF
    // ─────────────────────────────────────────────────────────────────────────

    public byte[] exportEffectifParServicePdf(String service, String grade, Integer annee) {
        List<VwEffectifParService> data = getEffectifParService(service, grade, annee);
        return exportService.exportToPdf(data, "effectif_par_service", buildParams("Effectif par Service", service, grade, annee));
    }

    public byte[] exportEffectifParGradePdf(String grade, String service, Integer annee) {
        List<VwEffectifParGrade> data = getEffectifParGrade(grade, service, annee);
        return exportService.exportToPdf(data, "effectif_par_grade", buildParams("Effectif par Grade", service, grade, annee));
    }

    public byte[] exportEffectifParAdmtechPdf(String service, Integer annee) {
        List<VwEffectifParAdmtech> data = getEffectifParAdmtech(service, annee);
        return exportService.exportToPdf(data, "effectif_par_admtech", buildParams("Effectif Administratif / Technique", service, null, annee));
    }

    public byte[] exportEffectifParSexePdf(String service, String grade, Integer annee) {
        List<VwEffectifParSexe> data = getEffectifParSexe(service, grade, annee);
        return exportService.exportToPdf(data, "effectif_par_sexe", buildParams("Effectif par Sexe", service, grade, annee));
    }

    public byte[] exportEffectifEvolutionPdf(String service, String grade, Integer annee) {
        List<VwEffectifEvolution> data = getEffectifEvolution(service, grade, annee);
        return exportService.exportToPdf(data, "effectif_evolution", buildParams("Évolution de l'Effectif", service, grade, annee));
    }

    public byte[] exportEffectifServiceGradePdf(String service, String grade, Integer annee) {
        List<VwEffectifServiceGrade> data = getEffectifServiceGrade(service, grade, annee);
        return exportService.exportToPdf(data, "effectif_service_grade", buildParams("Effectif Service / Grade", service, grade, annee));
    }

    public byte[] exportEffectifParAffectationPdf(String service, Integer annee) {
        List<VwEffectifParAffectation> data = getEffectifParAffectation(service, annee);
        return exportService.exportToPdf(data, "effectif_par_affectation", buildParams("Effectif par Affectation", service, null, annee));
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  UTILITAIRE PRIVÉ
    // ─────────────────────────────────────────────────────────────────────────

    private Map<String, Object> buildParams(String title, String service, String grade, Integer annee) {
        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_TITLE", title);
        if (service != null) params.put("service", service);
        if (grade   != null) params.put("grade",   grade);
        if (annee   != null) params.put("annee",   annee);
        return params;
    }
}