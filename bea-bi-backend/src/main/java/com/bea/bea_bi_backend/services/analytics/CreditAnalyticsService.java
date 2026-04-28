package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditAnalyticsService {

    private final VwEncoursParTypePretRepository encoursParTypePretRepo;
    private final VwTauxInteretPretRepository tauxInteretPretRepo;
    private final VwPretsParServiceRepository pretsParServiceRepo;
    private final VwCapitalRestantPretRepository capitalRestantPretRepo;
    private final VwPretsParDureeRepository pretsParDureeRepo;
    private final VwPretsParObjetRepository pretsParObjetRepo;
    private final VwTopEmprunteursRepository topEmprunteursRepo;

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
}