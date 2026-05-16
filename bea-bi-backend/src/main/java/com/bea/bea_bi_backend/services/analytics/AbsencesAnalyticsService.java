package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbsencesAnalyticsService {

    private final VwAbsencesParMotifRepository absencesParMotifRepo;
    private final VwAbsenteismeServiceRepository absenteismeServiceRepo;
    private final VwSoldeCongeTypeRepository soldeCongeTypeRepo;
    private final VwAbsencesParAnneeRepository absencesParAnneeRepo;
    private final VwTopAbsencesEmployeRepository topAbsencesEmployeRepo;
    private final VwDureeMoyenneCongeRepository dureeMoyenneCongeRepo;
    private final ExportService exportService;

    public List<VwAbsencesParMotif> getAbsencesParMotif(String service, Integer annee, String motif) {
        try {
            if (service != null || annee != null || motif != null) {
                return absencesParMotifRepo.findByFiltres(service, annee, motif);
            }
            return absencesParMotifRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des absences par motif", e);
            throw new RuntimeException("Impossible de récupérer les données d'absences", e);
        }
    }

    public List<VwAbsenteismeService> getAbsenteismeService(String service, Integer annee) {
        try {
            if (service != null || annee != null) {
                return absenteismeServiceRepo.findByFiltres(service, annee);
            }
            return absenteismeServiceRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'absentéisme par service", e);
            throw new RuntimeException("Impossible de récupérer les données d'absentéisme", e);
        }
    }

    public List<VwSoldeCongeType> getSoldeCongeType(String typCng, Integer annee) {
        try {
            if (typCng != null || annee != null) {
                return soldeCongeTypeRepo.findByFiltres(typCng, annee);
            }
            return soldeCongeTypeRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du solde congé par type", e);
            throw new RuntimeException("Impossible de récupérer les données de solde", e);
        }
    }

    public List<VwAbsencesParAnnee> getAbsencesParAnnee(Integer annee, String typCng, String service) {
        try {
            if (annee != null || typCng != null || service != null) {
                return absencesParAnneeRepo.findByFiltres(annee, typCng, service);
            }
            return absencesParAnneeRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des absences par année", e);
            throw new RuntimeException("Impossible de récupérer les données d'absences annuelles", e);
        }
    }

    public List<VwTopAbsencesEmploye> getTopAbsencesEmploye(String service, Integer annee) {
        try {
            if (service != null || annee != null) {
                return topAbsencesEmployeRepo.findByFiltres(service, annee);
            }
            return topAbsencesEmployeRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du top absences employé", e);
            throw new RuntimeException("Impossible de récupérer le top des absences", e);
        }
    }

    public List<VwDureeMoyenneConge> getDureeMoyenneConge(String service, Integer annee, String motif) {
        try {
            if (service != null || annee != null || motif != null) {
                return dureeMoyenneCongeRepo.findByFiltres(service, annee, motif);
            }
            return dureeMoyenneCongeRepo.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la durée moyenne de congé", e);
            throw new RuntimeException("Impossible de récupérer les durées moyennes", e);
        }
    }

    public byte[] exportAbsencesParMotifExcel(String service, Integer annee, String motif) {
        try {
            List<VwAbsencesParMotif> data = getAbsencesParMotif(service, annee, motif);
            return exportService.exportToExcel(
                    data,
                    "Absences par Motif",
                    new String[]{"service", "annee", "motif", "nombreAbsences", "totalJours"},
                    new String[]{"Service", "Année", "Motif", "Nombre d'absences", "Total jours"}
            );
        } catch (Exception e) {
            log.error("Erreur export Excel absences par motif", e);
            throw new RuntimeException("Échec de l'export Excel", e);
        }
    }

    public byte[] exportAbsenteismeServiceExcel(String service, Integer annee) {
        try {
            List<VwAbsenteismeService> data = getAbsenteismeService(service, annee);
            return exportService.exportToExcel(
                    data,
                    "Absentéisme par Service",
                    new String[]{"service", "annee", "tauxAbsenteisme", "nombreAbsences", "totalJours"},
                    new String[]{"Service", "Année", "Taux d'absentéisme (%)", "Nombre d'absences", "Total jours"}
            );
        } catch (Exception e) {
            log.error("Erreur export Excel absentéisme service", e);
            throw new RuntimeException("Échec de l'export Excel", e);
        }
    }

    public byte[] exportSoldeCongeTypeExcel(String typCng, Integer annee) {
        try {
            List<VwSoldeCongeType> data = getSoldeCongeType(typCng, annee);
            return exportService.exportToExcel(
                    data,
                    "Solde Congé par Type",
                    new String[]{"typCng", "annee", "soldeMoyen", "soldeTotal", "nombreEmployes"},
                    new String[]{"Type de congé", "Année", "Solde moyen", "Solde total", "Nb employés"}
            );
        } catch (Exception e) {
            log.error("Erreur export Excel solde congé type", e);
            throw new RuntimeException("Échec de l'export Excel", e);
        }
    }

    public byte[] exportAbsencesParAnneeExcel(Integer annee, String typCng, String service) {
        try {
            List<VwAbsencesParAnnee> data = getAbsencesParAnnee(annee, typCng, service);
            return exportService.exportToExcel(
                    data,
                    "Absences par Année",
                    new String[]{"annee", "typCng", "service", "nombreAbsences", "totalJours"},
                    new String[]{"Année", "Type de congé", "Service", "Nombre d'absences", "Total jours"}
            );
        } catch (Exception e) {
            log.error("Erreur export Excel absences par année", e);
            throw new RuntimeException("Échec de l'export Excel", e);
        }
    }

    public byte[] exportTopAbsencesEmployeExcel(String service, Integer annee) {
        try {
            List<VwTopAbsencesEmploye> data = getTopAbsencesEmploye(service, annee);
            return exportService.exportToExcel(
                    data,
                    "Top Absences Employé",
                    new String[]{"nomEmploye", "service", "annee", "nombreAbsences", "totalJours"},
                    new String[]{"Employé", "Service", "Année", "Nombre d'absences", "Total jours"}
            );
        } catch (Exception e) {
            log.error("Erreur export Excel top absences employé", e);
            throw new RuntimeException("Échec de l'export Excel", e);
        }
    }

    public byte[] exportDureeMoyenneCongeExcel(String service, Integer annee, String motif) {
        try {
            List<VwDureeMoyenneConge> data = getDureeMoyenneConge(service, annee, motif);
            return exportService.exportToExcel(
                    data,
                    "Durée Moyenne Congé",
                    new String[]{"service", "annee", "motif", "dureeMoyenne", "nombreConges"},
                    new String[]{"Service", "Année", "Motif", "Durée moyenne (j)", "Nombre de congés"}
            );
        } catch (Exception e) {
            log.error("Erreur export Excel durée moyenne congé", e);
            throw new RuntimeException("Échec de l'export Excel", e);
        }
    }

    public byte[] exportAbsencesParMotifPdf(String service, Integer annee, String motif) {
        try {
            List<VwAbsencesParMotif> data = getAbsencesParMotif(service, annee, motif);
            Map<String, Object> params = buildParams("Absences par Motif", service, annee);
            if (motif != null) params.put("motif", motif);
            return exportService.exportToPdf(data, "absences_par_motif", params);
        } catch (Exception e) {
            log.error("Erreur export PDF absences par motif", e);
            throw new RuntimeException("Échec de l'export PDF", e);
        }
    }

    public byte[] exportAbsenteismeServicePdf(String service, Integer annee) {
        try {
            List<VwAbsenteismeService> data = getAbsenteismeService(service, annee);
            return exportService.exportToPdf(data, "absenteisme_service", buildParams("Absentéisme par Service", service, annee));
        } catch (Exception e) {
            log.error("Erreur export PDF absentéisme service", e);
            throw new RuntimeException("Échec de l'export PDF", e);
        }
    }

    public byte[] exportSoldeCongeTypePdf(String typCng, Integer annee) {
        try {
            List<VwSoldeCongeType> data = getSoldeCongeType(typCng, annee);
            Map<String, Object> params = buildParams("Solde Congé par Type", null, annee);
            if (typCng != null) params.put("typCng", typCng);
            return exportService.exportToPdf(data, "solde_conge_type", params);
        } catch (Exception e) {
            log.error("Erreur export PDF solde congé type", e);
            throw new RuntimeException("Échec de l'export PDF", e);
        }
    }

    public byte[] exportAbsencesParAnneePdf(Integer annee, String typCng, String service) {
        try {
            List<VwAbsencesParAnnee> data = getAbsencesParAnnee(annee, typCng, service);
            Map<String, Object> params = buildParams("Absences par Année", service, annee);
            if (typCng != null) params.put("typCng", typCng);
            return exportService.exportToPdf(data, "absences_par_annee", params);
        } catch (Exception e) {
            log.error("Erreur export PDF absences par année", e);
            throw new RuntimeException("Échec de l'export PDF", e);
        }
    }

    public byte[] exportTopAbsencesEmployePdf(String service, Integer annee) {
        try {
            List<VwTopAbsencesEmploye> data = getTopAbsencesEmploye(service, annee);
            return exportService.exportToPdf(data, "top_absences_employe", buildParams("Top Absences Employé", service, annee));
        } catch (Exception e) {
            log.error("Erreur export PDF top absences employé", e);
            throw new RuntimeException("Échec de l'export PDF", e);
        }
    }

    public byte[] exportDureeMoyenneCongePdf(String service, Integer annee, String motif) {
        try {
            List<VwDureeMoyenneConge> data = getDureeMoyenneConge(service, annee, motif);
            Map<String, Object> params = buildParams("Durée Moyenne Congé", service, annee);
            if (motif != null) params.put("motif", motif);
            return exportService.exportToPdf(data, "duree_moyenne_conge", params);
        } catch (Exception e) {
            log.error("Erreur export PDF durée moyenne congé", e);
            throw new RuntimeException("Échec de l'export PDF", e);
        }
    }

    private Map<String, Object> buildParams(String title, String service, Integer annee) {
        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_TITLE", title);
        if (service != null) params.put("service", service);
        if (annee != null) params.put("annee", annee);
        return params;
    }
}