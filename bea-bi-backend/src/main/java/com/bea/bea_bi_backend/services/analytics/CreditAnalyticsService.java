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
public class CreditAnalyticsService {

    private final VwEncoursParTypePretRepository    encoursParTypePretRepo;
    private final VwTauxInteretPretRepository       tauxInteretPretRepo;
    private final VwPretsParServiceRepository       pretsParServiceRepo;
    private final VwCapitalRestantPretRepository    capitalRestantPretRepo;
    private final VwPretsParDureeRepository         pretsParDureeRepo;
    private final VwPretsParObjetRepository         pretsParObjetRepo;
    private final VwTopEmprunteursRepository        topEmprunteursRepo;
    private final ExportService                     exportService;

    // ─────────────────────────────────────────────────────────────────────────
    //  DONNÉES
    // ─────────────────────────────────────────────────────────────────────────

    public List<VwEncoursParTypePret> getEncoursParTypePret(String typPret, String service, Integer annee) {
        if (typPret != null || service != null || annee != null) {
            return encoursParTypePretRepo.findByFiltres(typPret, service, annee);
        }
        return encoursParTypePretRepo.findAll();
    }

    public List<VwTauxInteretPret> getTauxInteretPret(String typPret, Integer annee) {
        if (typPret != null || annee != null) {
            return tauxInteretPretRepo.findByFiltres(typPret, annee);
        }
        return tauxInteretPretRepo.findAll();
    }

    public List<VwPretsParService> getPretsParService(String service, String typPret, Integer annee) {
        if (service != null || typPret != null || annee != null) {
            return pretsParServiceRepo.findByFiltres(service, typPret, annee);
        }
        return pretsParServiceRepo.findAll();
    }

    public List<VwCapitalRestantPret> getCapitalRestantPret(String typPret, Integer annee) {
        if (typPret != null || annee != null) {
            return capitalRestantPretRepo.findByFiltres(typPret, annee);
        }
        return capitalRestantPretRepo.findAll();
    }

    public List<VwPretsParDuree> getPretsParDuree(String service, Integer annee, Integer duree) {
        if (service != null || annee != null || duree != null) {
            return pretsParDureeRepo.findByFiltres(service, annee, duree);
        }
        return pretsParDureeRepo.findAll();
    }

    public List<VwPretsParObjet> getPretsParObjet(String service, Integer annee, String objet) {
        if (service != null || annee != null || objet != null) {
            return pretsParObjetRepo.findByFiltres(service, annee, objet);
        }
        return pretsParObjetRepo.findAll();
    }

    public List<VwTopEmprunteurs> getTopEmprunteurs(String service, Integer annee) {
        if (service != null || annee != null) {
            return topEmprunteursRepo.findByFiltres(service, annee);
        }
        return topEmprunteursRepo.findAll();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT EXCEL
    // ─────────────────────────────────────────────────────────────────────────

    public byte[] exportEncoursParTypePretExcel(String typPret, String service, Integer annee) {
        List<VwEncoursParTypePret> data = getEncoursParTypePret(typPret, service, annee);
        return exportService.exportToExcel(
                data,
                "Encours par Type de Prêt",
                new String[]{"typPret", "service", "annee", "encours", "nombrePrets"},
                new String[]{"Type de prêt", "Service", "Année", "Encours (DA)", "Nb prêts"}
        );
    }

    public byte[] exportTauxInteretPretExcel(String typPret, Integer annee) {
        List<VwTauxInteretPret> data = getTauxInteretPret(typPret, annee);
        return exportService.exportToExcel(
                data,
                "Taux d'Intérêt par Prêt",
                new String[]{"typPret", "annee", "tauxInteret", "tauxMoyen", "nombrePrets"},
                new String[]{"Type de prêt", "Année", "Taux d'intérêt (%)", "Taux moyen (%)", "Nb prêts"}
        );
    }

    public byte[] exportPretsParServiceExcel(String service, String typPret, Integer annee) {
        List<VwPretsParService> data = getPretsParService(service, typPret, annee);
        return exportService.exportToExcel(
                data,
                "Prêts par Service",
                new String[]{"service", "typPret", "annee", "montantTotal", "nombrePrets", "montantMoyen"},
                new String[]{"Service", "Type de prêt", "Année", "Montant total (DA)", "Nb prêts", "Montant moyen (DA)"}
        );
    }

    public byte[] exportCapitalRestantPretExcel(String typPret, Integer annee) {
        List<VwCapitalRestantPret> data = getCapitalRestantPret(typPret, annee);
        return exportService.exportToExcel(
                data,
                "Capital Restant par Prêt",
                new String[]{"typPret", "annee", "capitalRestant", "capitalInitial", "tauxRembourse"},
                new String[]{"Type de prêt", "Année", "Capital restant (DA)", "Capital initial (DA)", "Taux remboursé (%)"}
        );
    }

    public byte[] exportPretsParDureeExcel(String service, Integer annee, Integer duree) {
        List<VwPretsParDuree> data = getPretsParDuree(service, annee, duree);
        return exportService.exportToExcel(
                data,
                "Prêts par Durée",
                new String[]{"service", "annee", "duree", "montantTotal", "nombrePrets"},
                new String[]{"Service", "Année", "Durée (mois)", "Montant total (DA)", "Nb prêts"}
        );
    }

    public byte[] exportPretsParObjetExcel(String service, Integer annee, String objet) {
        List<VwPretsParObjet> data = getPretsParObjet(service, annee, objet);
        return exportService.exportToExcel(
                data,
                "Prêts par Objet",
                new String[]{"service", "annee", "objet", "montantTotal", "nombrePrets"},
                new String[]{"Service", "Année", "Objet", "Montant total (DA)", "Nb prêts"}
        );
    }

    public byte[] exportTopEmprunteursExcel(String service, Integer annee) {
        List<VwTopEmprunteurs> data = getTopEmprunteurs(service, annee);
        return exportService.exportToExcel(
                data,
                "Top Emprunteurs",
                new String[]{"nomEmploye", "service", "annee", "montantTotal", "nombrePrets", "encours"},
                new String[]{"Employé", "Service", "Année", "Montant total (DA)", "Nb prêts", "Encours (DA)"}
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  EXPORT PDF
    // ─────────────────────────────────────────────────────────────────────────

    public byte[] exportEncoursParTypePretPdf(String typPret, String service, Integer annee) {
        List<VwEncoursParTypePret> data = getEncoursParTypePret(typPret, service, annee);
        Map<String, Object> params = buildParams("Encours par Type de Prêt", service, annee);
        if (typPret != null) params.put("typPret", typPret);
        return exportService.exportToPdf(data, "encours_par_type_pret", params);
    }

    public byte[] exportTauxInteretPretPdf(String typPret, Integer annee) {
        List<VwTauxInteretPret> data = getTauxInteretPret(typPret, annee);
        Map<String, Object> params = buildParams("Taux d'Intérêt par Prêt", null, annee);
        if (typPret != null) params.put("typPret", typPret);
        return exportService.exportToPdf(data, "taux_interet_pret", params);
    }

    public byte[] exportPretsParServicePdf(String service, String typPret, Integer annee) {
        List<VwPretsParService> data = getPretsParService(service, typPret, annee);
        Map<String, Object> params = buildParams("Prêts par Service", service, annee);
        if (typPret != null) params.put("typPret", typPret);
        return exportService.exportToPdf(data, "prets_par_service", params);
    }

    public byte[] exportCapitalRestantPretPdf(String typPret, Integer annee) {
        List<VwCapitalRestantPret> data = getCapitalRestantPret(typPret, annee);
        Map<String, Object> params = buildParams("Capital Restant par Prêt", null, annee);
        if (typPret != null) params.put("typPret", typPret);
        return exportService.exportToPdf(data, "capital_restant_pret", params);
    }

    public byte[] exportPretsParDureePdf(String service, Integer annee, Integer duree) {
        List<VwPretsParDuree> data = getPretsParDuree(service, annee, duree);
        Map<String, Object> params = buildParams("Prêts par Durée", service, annee);
        if (duree != null) params.put("duree", duree);
        return exportService.exportToPdf(data, "prets_par_duree", params);
    }

    public byte[] exportPretsParObjetPdf(String service, Integer annee, String objet) {
        List<VwPretsParObjet> data = getPretsParObjet(service, annee, objet);
        Map<String, Object> params = buildParams("Prêts par Objet", service, annee);
        if (objet != null) params.put("objet", objet);
        return exportService.exportToPdf(data, "prets_par_objet", params);
    }

    public byte[] exportTopEmprunteursPdf(String service, Integer annee) {
        List<VwTopEmprunteurs> data = getTopEmprunteurs(service, annee);
        return exportService.exportToPdf(data, "top_emprunteurs", buildParams("Top Emprunteurs", service, annee));
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  UTILITAIRE PRIVÉ
    // ─────────────────────────────────────────────────────────────────────────

    private Map<String, Object> buildParams(String title, String service, Integer annee) {
        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_TITLE", title);
        if (service != null) params.put("service", service);
        if (annee  != null) params.put("annee",   annee);
        return params;
    }
}