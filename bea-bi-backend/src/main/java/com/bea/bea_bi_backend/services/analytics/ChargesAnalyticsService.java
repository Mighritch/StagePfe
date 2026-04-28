package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargesAnalyticsService {

    private final VwMasseSalarialeMoisRepository masseSalarialeMoisRepo;
    private final VwChargesParTypeBulletinRepository chargesParTypeBulletinRepo;
    private final VwMasseSalarialeServiceRepository masseSalarialeServiceRepo;
    private final VwSalaireParGradeRepository salaireParGradeRepo;
    private final VwEvolutionMasseSalarialeRepository evolutionMasseSalarialeRepo;
    private final VwTauxChargeServiceRepository tauxChargeServiceRepo;

    public List<VwMasseSalarialeMois> getMasseSalarialeMois(String service, Integer annee, Integer mois) {
        if (service != null || annee != null || mois != null) {
            return masseSalarialeMoisRepo.findByFiltres(service, annee, mois);
        }
        return masseSalarialeMoisRepo.findAll();
    }

    public List<VwChargesParTypeBulletin> getChargesParTypeBulletin(String service, Integer annee) {
        if (service != null || annee != null) {
            return chargesParTypeBulletinRepo.findByFiltres(service, annee);
        }
        return chargesParTypeBulletinRepo.findAll();
    }

    public List<VwMasseSalarialeService> getMasseSalarialeService(String service, Integer annee) {
        if (service != null || annee != null) {
            return masseSalarialeServiceRepo.findByFiltres(service, annee);
        }
        return masseSalarialeServiceRepo.findAll();
    }

    public List<VwSalaireParGrade> getSalaireParGrade(String grade, String service, Integer annee) {
        if (grade != null || service != null || annee != null) {
            return salaireParGradeRepo.findByFiltres(grade, service, annee);
        }
        return salaireParGradeRepo.findAll();
    }

    public List<VwEvolutionMasseSalariale> getEvolutionMasseSalariale(Integer annee) {
        if (annee != null) {
            return evolutionMasseSalarialeRepo.findByFiltres(annee);
        }
        return evolutionMasseSalarialeRepo.findAll();
    }

    public List<VwTauxChargeService> getTauxChargeService(String service, Integer annee) {
        if (service != null || annee != null) {
            return tauxChargeServiceRepo.findByFiltres(service, annee);
        }
        return tauxChargeServiceRepo.findAll();
    }
}