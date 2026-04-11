package com.bea.bea_bi_backend.services;

import com.bea.bea_bi_backend.dto.*;
import com.bea.bea_bi_backend.repositories.DemCngRepository;
import com.bea.bea_bi_backend.repositories.SoldCngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbsenceService {

    private final DemCngRepository demCngRepository;
    private final SoldCngRepository soldCngRepository;

    // ── Absences par motif ────────────────────────────────────────────────────
    public List<AbsenceParMotifDTO> getAbsencesParMotif(
            String codSoc, LocalDate debut, LocalDate fin) {
        return demCngRepository.sumJoursByMotifAndPeriode(codSoc, debut, fin)
                .stream()
                .map(row -> new AbsenceParMotifDTO(
                        (String) row[0],
                        (BigDecimal) row[1]))
                .collect(Collectors.toList());
    }

    // ── Absences par service ──────────────────────────────────────────────────
    public List<AbsenceParServiceDTO> getAbsencesParService(
            String codSoc, LocalDate debut, LocalDate fin) {
        return demCngRepository.sumJoursByServiceAndPeriode(codSoc, debut, fin)
                .stream()
                .map(row -> new AbsenceParServiceDTO(
                        (String) row[0],
                        (BigDecimal) row[1]))
                .collect(Collectors.toList());
    }

    // ── Absences par année et motif ───────────────────────────────────────────
    public List<AbsenceParMotifDTO> getAbsencesParAnnee(String codSoc) {
        return demCngRepository.sumJoursByAnneeAndMotif(codSoc)
                .stream()
                .map(row -> new AbsenceParMotifDTO(
                        row[0] + "-" + row[1],   // "2024-CNG"
                        (BigDecimal) row[2]))
                .collect(Collectors.toList());
    }

    // ── Demandes en attente ───────────────────────────────────────────────────
    public List<SoldeCongeDTO> getDemandesEnAttente(String codSoc) {
        return demCngRepository.findDemandesEnAttente(codSoc)
                .stream()
                .map(d -> new SoldeCongeDTO(
                        d.getId().getMatPers(),
                        d.getAnneeCng(),
                        d.getNatCng(),
                        null,
                        d.getNbrJours(),
                        d.getSoldCng(),
                        null))
                .collect(Collectors.toList());
    }

    // ── Historique congés d'un agent ─────────────────────────────────────────
    public List<SoldeCongeDTO> getDemandesByMatricule(String codSoc, String matPers) {
        return demCngRepository.findBySocieteAndMatricule(codSoc, matPers)
                .stream()
                .map(d -> new SoldeCongeDTO(
                        d.getId().getMatPers(),
                        d.getAnneeCng(),
                        d.getNatCng(),
                        null,
                        d.getNbrJours(),
                        d.getSoldCng(),
                        null))
                .collect(Collectors.toList());
    }

    // ── Soldes par type de congé ──────────────────────────────────────────────
    public List<SoldeCongeDTO> getSoldesParType(String codSoc, Integer annee) {
        return soldCngRepository.statsSoldesByTypeConge(codSoc, annee)
                .stream()
                .map(row -> new SoldeCongeDTO(
                        null,
                        annee,
                        (String) row[0],
                        null,
                        null,
                        (BigDecimal) row[1],
                        null))
                .collect(Collectors.toList());
    }

    // ── Agents avec solde positif ─────────────────────────────────────────────
    public List<SoldeCongeDTO> getSoldesPositifs(String codSoc, Integer annee) {
        return soldCngRepository.findSoldesPositifsByAnnee(codSoc, annee)
                .stream()
                .map(s -> new SoldeCongeDTO(
                        s.getId().getMatPers(),
                        s.getId().getAnneeCng(),
                        s.getId().getTypCng(),
                        s.getInitCng(),
                        s.getPrisCng(),
                        s.getSoldCng(),
                        s.getCumCng()))
                .collect(Collectors.toList());
    }

    // ── Évolution congés par année ────────────────────────────────────────────
    public List<SoldeCongeDTO> getEvolutionConges(String codSoc) {
        return soldCngRepository.evolutionCongesParAnnee(codSoc)
                .stream()
                .map(row -> new SoldeCongeDTO(
                        null,
                        (Integer) row[0],
                        (String) row[1],
                        null,
                        (BigDecimal) row[2],
                        (BigDecimal) row[3],
                        null))
                .collect(Collectors.toList());
    }

    // ── Absences non justifiées ───────────────────────────────────────────────
    public Integer getAbsencesNonJustifiees(String codSoc, Integer annee) {
        Integer total = soldCngRepository.sumAbsencesNonJustifieesParAnnee(codSoc, annee);
        return total != null ? total : 0;
    }
}