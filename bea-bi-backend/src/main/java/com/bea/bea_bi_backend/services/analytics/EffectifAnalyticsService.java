package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EffectifAnalyticsService {

    private final VwEffectifParServiceRepository effectifParServiceRepo;
    private final VwEffectifParGradeRepository effectifParGradeRepo;
    private final VwEffectifParAdmtechRepository effectifParAdmtechRepo;
    private final VwEffectifParSexeRepository effectifParSexeRepo;
    private final VwEffectifEvolutionRepository effectifEvolutionRepo;
    private final VwEffectifServiceGradeRepository effectifServiceGradeRepo;
    private final VwEffectifParAffectationRepository effectifParAffectationRepo;

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
}