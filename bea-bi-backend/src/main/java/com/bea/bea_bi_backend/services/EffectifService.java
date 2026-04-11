package com.bea.bea_bi_backend.services;

import com.bea.bea_bi_backend.dto.*;
import com.bea.bea_bi_backend.entities.PersonnelHist;
import com.bea.bea_bi_backend.repositories.PersonnelHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EffectifService {

    private final PersonnelHistRepository personnelHistRepository;

    // ── Effectif par service ──────────────────────────────────────────────────
    public List<EffectifParServiceDTO> getEffectifParService(String codSoc) {
        return personnelHistRepository.countActifsByService(codSoc)
                .stream()
                .map(row -> new EffectifParServiceDTO(
                        (String) row[0],
                        (Long) row[1]))
                .collect(Collectors.toList());
    }

    // ── Effectif par grade ────────────────────────────────────────────────────
    public List<EffectifParGradeDTO> getEffectifParGrade(String codSoc) {
        return personnelHistRepository.countActifsByGrade(codSoc)
                .stream()
                .map(row -> new EffectifParGradeDTO(
                        (String) row[0],
                        (Long) row[1]))
                .collect(Collectors.toList());
    }

    // ── Effectif par sexe ─────────────────────────────────────────────────────
    public List<EffectifParSexeDTO> getEffectifParSexe(String codSoc) {
        return personnelHistRepository.countActifsBySexe(codSoc)
                .stream()
                .map(row -> new EffectifParSexeDTO(
                        (String) row[0],
                        (Long) row[1]))
                .collect(Collectors.toList());
    }

    // ── Effectif par admTech ──────────────────────────────────────────────────
    public List<EffectifParAdmTechDTO> getEffectifParAdmTech(String codSoc) {
        return personnelHistRepository.countActifsByAdmTech(codSoc)
                .stream()
                .map(row -> new EffectifParAdmTechDTO(
                        (String) row[0],
                        (Long) row[1]))
                .collect(Collectors.toList());
    }

    // ── Effectif total ────────────────────────────────────────────────────────
    public Long getEffectifTotal(String codSoc) {
        return personnelHistRepository.countActifsBySociete(codSoc);
    }

    // ── Entrées sur une période ───────────────────────────────────────────────
    public List<MouvementPersonnelDTO> getEntrees(String codSoc, LocalDate debut, LocalDate fin) {
        return personnelHistRepository.findEntreesBySocieteAndPeriode(codSoc, debut, fin)
                .stream()
                .map(p -> new MouvementPersonnelDTO(
                        p.getMatPers(),
                        p.getNomPers(),
                        p.getPrenPers(),
                        p.getCodServ(),
                        p.getCodGrad(),
                        p.getDatEnt(),
                        "ENTREE"))
                .collect(Collectors.toList());
    }

    // ── Départs sur une période ───────────────────────────────────────────────
    public List<MouvementPersonnelDTO> getDeparts(String codSoc, LocalDate debut, LocalDate fin) {
        return personnelHistRepository.findDepartsBySocieteAndPeriode(codSoc, debut, fin)
                .stream()
                .map(p -> new MouvementPersonnelDTO(
                        p.getMatPers(),
                        p.getNomPers(),
                        p.getPrenPers(),
                        p.getCodServ(),
                        p.getCodGrad(),
                        p.getDatDepart(),
                        "DEPART"))
                .collect(Collectors.toList());
    }

    // ── Taux de rotation ──────────────────────────────────────────────────────
    public TauxRotationDTO getTauxRotation(String codSoc, LocalDate debut, LocalDate fin) {
        long entrees = personnelHistRepository
                .findEntreesBySocieteAndPeriode(codSoc, debut, fin).size();
        long departs = personnelHistRepository
                .findDepartsBySocieteAndPeriode(codSoc, debut, fin).size();
        long effectifTotal = personnelHistRepository.countActifsBySociete(codSoc);

        double taux = effectifTotal == 0 ? 0.0
                : ((entrees + departs) / 2.0 / effectifTotal) * 100.0;

        return new TauxRotationDTO(debut, fin, entrees, departs, effectifTotal,
                Math.round(taux * 100.0) / 100.0);
    }

    // ── Agents handicapés ─────────────────────────────────────────────────────
    public List<HandicapeDTO> getHandicapes(String codSoc) {
        return personnelHistRepository.findHandicapesBySociete(codSoc)
                .stream()
                .map(p -> new HandicapeDTO(
                        p.getMatPers(),
                        p.getNomPers(),
                        p.getPrenPers(),
                        p.getCodServ(),
                        p.getPourcentHand()))
                .collect(Collectors.toList());
    }
}