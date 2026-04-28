package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AbsencesAnalyticsService {

    private final VwAbsencesParMotifRepository absencesParMotifRepo;
    private final VwAbsenteismeServiceRepository absenteismeServiceRepo;
    private final VwSoldeCongeTypeRepository soldeCongeTypeRepo;
    private final VwAbsencesParAnneeRepository absencesParAnneeRepo;
    private final VwTopAbsencesEmployeRepository topAbsencesEmployeRepo;
    private final VwDureeMoyenneCongeRepository dureeMoyenneCongeRepo;

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
}