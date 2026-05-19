package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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
    
    private final EntityManager entityManager;

    // ============================================================
    // DONNÉES
    // ============================================================

    public List<VwEffectifParService> getEffectifParService(String service, String grade, Integer annee) {
        try {
            if (service != null || grade != null || annee != null) {
                return effectifParServiceRepo.findByFiltres(service, grade, annee);
            }
            return effectifParServiceRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération effectif par service", e);
            throw new RuntimeException("Impossible de récupérer l'effectif par service", e);
        }
    }

    public List<VwEffectifParGrade> getEffectifParGrade(String grade, String service, Integer annee) {
        try {
            if (grade != null || service != null || annee != null) {
                return effectifParGradeRepo.findByFiltres(grade, service, annee);
            }
            return effectifParGradeRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération effectif par grade", e);
            throw new RuntimeException("Impossible de récupérer l'effectif par grade", e);
        }
    }

    public List<VwEffectifParAdmtech> getEffectifParAdmtech(String service, Integer annee) {
        try {
            if (service != null || annee != null) {
                return effectifParAdmtechRepo.findByFiltres(service, annee);
            }
            return effectifParAdmtechRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération effectif admin/tech", e);
            throw new RuntimeException("Impossible de récupérer l'effectif admin/tech", e);
        }
    }

    public List<VwEffectifParSexe> getEffectifParSexe(String service, String grade, Integer annee) {
        try {
            if (service != null || grade != null || annee != null) {
                return effectifParSexeRepo.findByFiltres(service, grade, annee);
            }
            return effectifParSexeRepo.findAll();
        } catch (Exception e) {
            log.warn("Erreur Repository, tentative via Fallback native query", e);
            return getEffectifParSexeFallback();
        }
    }

    @SuppressWarnings("unchecked")
    public List<VwEffectifParSexe> getEffectifParSexeFallback() {
        try {
            String sql = """
                SELECT 
                    ROW_NUMBER() OVER (ORDER BY p.SEXE) as ROW_ID,
                    p.SEXE as sexe,
                    SUM(f.NB_EFFECTIF) as nbEmployes,
                    ROUND(SUM(f.NB_EFFECTIF) * 100.0 / SUM(SUM(f.NB_EFFECTIF)) OVER(), 2) as pourcentage,
                    NULL as libServ,
                    NULL as libGrad,
                    EXTRACT(YEAR FROM SYSDATE) as annee
                FROM FACT_EFFECTIF f
                JOIN DIM_PERSONNEL p ON p.PERSONNEL_KEY = f.PERSONNEL_KEY
                GROUP BY p.SEXE
            """;
            
            Query query = entityManager.createNativeQuery(sql, VwEffectifParSexe.class);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Fallback query failed", e);
            return List.of();
        }
    }

    public List<VwEffectifEvolution> getEffectifEvolution(String service, String grade, Integer annee) {
        try {
            if (service != null || grade != null || annee != null) {
                return effectifEvolutionRepo.findByFiltres(service, grade, annee);
            }
            return effectifEvolutionRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération évolution de l'effectif", e);
            throw new RuntimeException("Impossible de récupérer l'évolution de l'effectif", e);
        }
    }

    public List<VwEffectifServiceGrade> getEffectifServiceGrade(String service, String grade, Integer annee) {
        try {
            if (service != null || grade != null || annee != null) {
                return effectifServiceGradeRepo.findByFiltres(service, grade, annee);
            }
            return effectifServiceGradeRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération effectif service/grade", e);
            throw new RuntimeException("Impossible de récupérer l'effectif service/grade", e);
        }
    }

    public List<VwEffectifParAffectation> getEffectifParAffectation(String service, Integer annee) {
        try {
            if (service != null || annee != null) {
                return effectifParAffectationRepo.findByFiltres(service, annee);
            }
            return effectifParAffectationRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération effectif par affectation", e);
            throw new RuntimeException("Impossible de récupérer l'effectif par affectation", e);
        }
    }

    // ============================================================
    // EXPORT EXCEL
    // ============================================================

    public byte[] exportEffectifParServiceExcel(String service, String grade, Integer annee) {
        List<VwEffectifParService> data = getEffectifParService(service, grade, annee);
        return exportService.exportToExcel(
                data,
                "Effectif par Service",
                new String[]{"libServ", "typeServ", "libGrad", "nbEmployes"},
                new String[]{"Service", "Type", "Grade", "Effectif"}
        );
    }

    public byte[] exportEffectifParGradeExcel(String grade, String service, Integer annee) {
        List<VwEffectifParGrade> data = getEffectifParGrade(grade, service, annee);
        return exportService.exportToExcel(
                data,
                "Effectif par Grade",
                new String[]{"libGrad", "codCateg", "libServ", "nbEmployes", "pourcentage"},
                new String[]{"Grade", "Catégorie", "Service", "Effectif", "Pourcentage (%)"}
        );
    }

    public byte[] exportEffectifParAdmtechExcel(String service, Integer annee) {
        List<VwEffectifParAdmtech> data = getEffectifParAdmtech(service, annee);
        return exportService.exportToExcel(
                data,
                "Effectif Administratif / Technique",
                new String[]{"libServ", "annee", "categorie", "effectif", "pourcentage"},
                new String[]{"Service", "Année", "Catégorie", "Effectif", "Pourcentage (%)"}
        );
    }

    public byte[] exportEffectifParSexeExcel(String service, String grade, Integer annee) {
        List<VwEffectifParSexe> data = getEffectifParSexe(service, grade, annee);
        return exportService.exportToExcel(
                data,
                "Effectif par Sexe",
                new String[]{"sexe", "nbEmployes", "pourcentage", "libServ", "libGrad", "annee"},
                new String[]{"Sexe", "Effectif", "Pourcentage (%)", "Service", "Grade", "Année"}
        );
    }

    public byte[] exportEffectifEvolutionExcel(String service, String grade, Integer annee) {
        List<VwEffectifEvolution> data = getEffectifEvolution(service, grade, annee);
        return exportService.exportToExcel(
                data,
                "Évolution de l'Effectif",
                new String[]{"libServ", "libGrad", "annee", "effectif", "evolution", "tauxEvolution"},
                new String[]{"Service", "Grade", "Année", "Effectif", "Évolution", "Taux d'évolution (%)"}
        );
    }

    public byte[] exportEffectifServiceGradeExcel(String service, String grade, Integer annee) {
        List<VwEffectifServiceGrade> data = getEffectifServiceGrade(service, grade, annee);
        return exportService.exportToExcel(
                data,
                "Effectif Service / Grade",
                new String[]{"libServ", "libGrad", "annee", "effectif"},
                new String[]{"Service", "Grade", "Année", "Effectif"}
        );
    }

    public byte[] exportEffectifParAffectationExcel(String service, Integer annee) {
        List<VwEffectifParAffectation> data = getEffectifParAffectation(service, annee);
        return exportService.exportToExcel(
                data,
                "Effectif par Affectation",
                new String[]{"libServ", "annee", "affectation", "effectif", "pourcentage"},
                new String[]{"Service", "Année", "Affectation", "Effectif", "Pourcentage (%)"}
        );
    }

    // ============================================================
    // EXPORT PDF
    // ============================================================

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

    private Map<String, Object> buildParams(String title, String service, String grade, Integer annee) {
        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_TITLE", title);
        if (service != null) params.put("service", service);
        if (grade != null) params.put("grade", grade);
        if (annee != null) params.put("annee", annee);
        return params;
    }
}