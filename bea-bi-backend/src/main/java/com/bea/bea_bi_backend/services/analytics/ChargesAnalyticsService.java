package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargesAnalyticsService {

    private final VwMasseSalarialeMoisRepository       masseSalarialeMoisRepo;
    private final VwChargesParTypeBulletinRepository   chargesParTypeBulletinRepo;
    private final VwMasseSalarialeServiceRepository    masseSalarialeServiceRepo;
    private final VwSalaireParGradeRepository          salaireParGradeRepo;
    private final VwEvolutionMasseSalarialeRepository  evolutionMasseSalarialeRepo;
    private final VwTauxChargeServiceRepository        tauxChargeServiceRepo;
    private final ExportService                        exportService;

    public List<VwMasseSalarialeMois> getMasseSalarialeMois(String service, Integer annee, Integer mois) {
        try {
            if (service != null || annee != null || mois != null) {
                return masseSalarialeMoisRepo.findByFiltres(service, annee, mois);
            }
            return masseSalarialeMoisRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération masse salariale par mois", e);
            throw new RuntimeException("Impossible de récupérer les données de masse salariale mensuelle", e);
        }
    }

    public List<VwChargesParTypeBulletin> getChargesParTypeBulletin(String service, Integer annee) {
        try {
            if (service != null || annee != null) {
                return chargesParTypeBulletinRepo.findByFiltres(service, annee);
            }
            return chargesParTypeBulletinRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération charges par type bulletin", e);
            throw new RuntimeException("Impossible de récupérer les charges par type de bulletin", e);
        }
    }

    public List<VwMasseSalarialeService> getMasseSalarialeService(String service, Integer annee) {
        try {
            if (service != null || annee != null) {
                return masseSalarialeServiceRepo.findByFiltres(service, annee);
            }
            return masseSalarialeServiceRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération masse salariale par service", e);
            throw new RuntimeException("Impossible de récupérer la masse salariale par service", e);
        }
    }

    public List<VwSalaireParGrade> getSalaireParGrade(String grade, String service, Integer annee) {
        try {
            if (grade != null || service != null || annee != null) {
                return salaireParGradeRepo.findByFiltres(grade, service, annee);
            }
            return salaireParGradeRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération salaire par grade", e);
            throw new RuntimeException("Impossible de récupérer les salaires par grade", e);
        }
    }

    public List<VwEvolutionMasseSalariale> getEvolutionMasseSalariale(Integer annee) {
        try {
            if (annee != null) {
                return evolutionMasseSalarialeRepo.findByFiltres(annee);
            }
            return evolutionMasseSalarialeRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur sur evolution masse salariale", e);
            return List.of();
        }
    }

    public List<VwTauxChargeService> getTauxChargeService(String service, Integer annee) {
        try {
            if (service != null || annee != null) {
                return tauxChargeServiceRepo.findByFiltres(service, annee);
            }
            return tauxChargeServiceRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur récupération taux de charge par service", e);
            throw new RuntimeException("Impossible de récupérer les taux de charge", e);
        }
    }

    public byte[] exportMasseSalarialeMoisExcel(String service, Integer annee, Integer mois) {
        List<VwMasseSalarialeMois> data = getMasseSalarialeMois(service, annee, mois);
        return exportService.exportToExcel(
                data,
                "Masse Salariale par Mois",
                new String[]{"service", "annee", "mois", "masseSalariale", "nombreBulletins"},
                new String[]{"Service", "Année", "Mois", "Masse salariale (DA)", "Nb bulletins"}
        );
    }

    public byte[] exportChargesParTypeBulletinExcel(String service, Integer annee) {
        List<VwChargesParTypeBulletin> data = getChargesParTypeBulletin(service, annee);
        return exportService.exportToExcel(
                data,
                "Charges par Type de Bulletin",
                new String[]{"service", "annee", "typeBulletin", "totalCharges", "nombreBulletins"},
                new String[]{"Service", "Année", "Type bulletin", "Total charges (DA)", "Nb bulletins"}
        );
    }

    public byte[] exportMasseSalarialeServiceExcel(String service, Integer annee) {
        List<VwMasseSalarialeService> data = getMasseSalarialeService(service, annee);
        return exportService.exportToExcel(
                data,
                "Masse Salariale par Service",
                new String[]{"service", "annee", "masseSalariale", "salaireMoyen", "nombreEmployes"},
                new String[]{"Service", "Année", "Masse salariale (DA)", "Salaire moyen (DA)", "Nb employés"}
        );
    }

    public byte[] exportSalaireParGradeExcel(String grade, String service, Integer annee) {
        List<VwSalaireParGrade> data = getSalaireParGrade(grade, service, annee);
        return exportService.exportToExcel(
                data,
                "Salaire par Grade",
                new String[]{"grade", "service", "annee", "salaireMoyen", "salaireMin", "salaireMax", "nombreEmployes"},
                new String[]{"Grade", "Service", "Année", "Salaire moyen (DA)", "Salaire min (DA)", "Salaire max (DA)", "Nb employés"}
        );
    }

    public byte[] exportEvolutionMasseSalarialeExcel(Integer annee) {
        List<VwEvolutionMasseSalariale> data = getEvolutionMasseSalariale(annee);
        return exportService.exportToExcel(
                data,
                "Évolution Masse Salariale",
                new String[]{"annee", "mois", "masseSalariale", "evolution", "tauxEvolution"},
                new String[]{"Année", "Mois", "Masse salariale (DA)", "Évolution (DA)", "Taux d'évolution (%)"}
        );
    }

    public byte[] exportTauxChargeServiceExcel(String service, Integer annee) {
        List<VwTauxChargeService> data = getTauxChargeService(service, annee);
        return exportService.exportToExcel(
                data,
                "Taux de Charge par Service",
                new String[]{"service", "annee", "tauxCharge", "totalCharges", "masseSalariale"},
                new String[]{"Service", "Année", "Taux de charge (%)", "Total charges (DA)", "Masse salariale (DA)"}
        );
    }

    public byte[] exportMasseSalarialeMoisPdf(String service, Integer annee, Integer mois) {
        List<VwMasseSalarialeMois> data = getMasseSalarialeMois(service, annee, mois);
        Map<String, Object> params = buildParams("Masse Salariale par Mois", service, annee);
        if (mois != null) params.put("mois", mois);
        return exportService.exportToPdf(data, "masse_salariale_mois", params);
    }

    public byte[] exportChargesParTypeBulletinPdf(String service, Integer annee) {
        List<VwChargesParTypeBulletin> data = getChargesParTypeBulletin(service, annee);
        return exportService.exportToPdf(data, "charges_par_type_bulletin", buildParams("Charges par Type de Bulletin", service, annee));
    }

    public byte[] exportMasseSalarialeServicePdf(String service, Integer annee) {
        List<VwMasseSalarialeService> data = getMasseSalarialeService(service, annee);
        return exportService.exportToPdf(data, "masse_salariale_service", buildParams("Masse Salariale par Service", service, annee));
    }

    public byte[] exportSalaireParGradePdf(String grade, String service, Integer annee) {
        List<VwSalaireParGrade> data = getSalaireParGrade(grade, service, annee);
        Map<String, Object> params = buildParams("Salaire par Grade", service, annee);
        if (grade != null) params.put("grade", grade);
        return exportService.exportToPdf(data, "salaire_par_grade", params);
    }

    public byte[] exportEvolutionMasseSalarialePdf(Integer annee) {
        List<VwEvolutionMasseSalariale> data = getEvolutionMasseSalariale(annee);
        return exportService.exportToPdf(data, "evolution_masse_salariale", buildParams("Évolution Masse Salariale", null, annee));
    }

    public byte[] exportTauxChargeServicePdf(String service, Integer annee) {
        List<VwTauxChargeService> data = getTauxChargeService(service, annee);
        return exportService.exportToPdf(data, "taux_charge_service", buildParams("Taux de Charge par Service", service, annee));
    }

    private Map<String, Object> buildParams(String title, String service, Integer annee) {
        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_TITLE", title);
        if (service != null) params.put("service", service);
        if (annee  != null) params.put("annee",   annee);
        return params;
    }
}