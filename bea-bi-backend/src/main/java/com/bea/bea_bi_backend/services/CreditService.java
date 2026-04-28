package com.bea.bea_bi_backend.services;

import com.bea.bea_bi_backend.dto.*;
import com.bea.bea_bi_backend.repositories.LigPretRepository;
import com.bea.bea_bi_backend.repositories.PretPersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final PretPersRepository pretPersRepository;
    private final LigPretRepository ligPretRepository;

    // ── Prêts en cours ────────────────────────────────────────────────────────
    public List<PretEnCoursDTO> getPretsEnCours(String codSoc) {
        return pretPersRepository.findPretsEnCoursBySociete(codSoc)
                .stream()
                .map(p -> new PretEnCoursDTO(
                        p.getId().getMatPers(),
                        p.getCodServ(),
                        p.getCodGrad(),
                        p.getTypPret(),
                        p.getPrtMntGlb(),
                        p.getPrtMntRem(),
                        p.getPrtMntGlb().subtract(p.getPrtMntRem() != null ? p.getPrtMntRem() : BigDecimal.ZERO),
                        p.getRemMen(),
                        p.getPrtDatAcc(),
                        p.getPrtDatFin()
                ))
                .collect(Collectors.toList());
    }

    // ── Prêts en cours par service ────────────────────────────────────────────
    public List<PretEnCoursDTO> getPretsEnCoursByService(String codSoc, String codServ) {
        return pretPersRepository.findPretsEnCoursByService(codSoc, codServ)
                .stream()
                .map(p -> new PretEnCoursDTO(
                        p.getId().getMatPers(),
                        p.getCodServ(),
                        p.getCodGrad(),
                        p.getTypPret(),
                        p.getPrtMntGlb(),
                        p.getPrtMntRem(),
                        p.getPrtMntGlb().subtract(p.getPrtMntRem() != null ? p.getPrtMntRem() : BigDecimal.ZERO),
                        p.getRemMen(),
                        p.getPrtDatAcc(),
                        p.getPrtDatFin()
                ))
                .collect(Collectors.toList());
    }

    // ── Stats par type de prêt ────────────────────────────────────────────────
    public List<StatsCreditDTO> getStatsParType(String codSoc) {
        return pretPersRepository.statsPretsEnCoursByType(codSoc)
                .stream()
                .map(row -> new StatsCreditDTO(
                        (String) row[0],
                        (Long) row[1],
                        (BigDecimal) row[2],
                        null))
                .collect(Collectors.toList());
    }

    // ── Stats par service ─────────────────────────────────────────────────────
    public List<StatsCreditDTO> getStatsParService(String codSoc) {
        return pretPersRepository.statsPretsEnCoursByService(codSoc)
                .stream()
                .map(row -> new StatsCreditDTO(
                        (String) row[0],
                        (Long) row[1],
                        (BigDecimal) row[2],
                        (BigDecimal) row[3]))
                .collect(Collectors.toList());
    }

    // ── Stats par grade ───────────────────────────────────────────────────────
    public List<StatsCreditDTO> getStatsParGrade(String codSoc) {
        return pretPersRepository.statsPretsEnCoursByGrade(codSoc)
                .stream()
                .map(row -> new StatsCreditDTO(
                        (String) row[0],
                        (Long) row[1],
                        (BigDecimal) row[2],
                        null))
                .collect(Collectors.toList());
    }

    // ── Encours total ─────────────────────────────────────────────────────────
    public BigDecimal getEncoursTotalBySociete(String codSoc) {
        BigDecimal encours = pretPersRepository.sumEncoursTotalBySociete(codSoc);
        return encours != null ? encours : BigDecimal.ZERO;
    }

    // ── Montant accordé sur une période ──────────────────────────────────────
    public BigDecimal getMontantAccorde(String codSoc, LocalDate debut, LocalDate fin) {
        BigDecimal montant = pretPersRepository
                .sumMontantAccordeBySocieteAndPeriode(codSoc, debut, fin);
        return montant != null ? montant : BigDecimal.ZERO;
    }

    // ── Évolution mensuelle ───────────────────────────────────────────────────
    public List<EvolutionPretDTO> getEvolutionMensuelle(
            String codSoc, LocalDate debut, LocalDate fin) {
        return pretPersRepository.evolutionMensuellePretsBySociete(codSoc, debut, fin)
                .stream()
                .map(row -> new EvolutionPretDTO(
                        (String) row[0],
                        (Long) row[1],
                        (BigDecimal) row[2]))
                .collect(Collectors.toList());
    }

    // ── Impayés ───────────────────────────────────────────────────────────────
    public List<ImpayeDTO> getImpayes(String codSoc) {
        return ligPretRepository.statsImpayesByMatricule(codSoc)
                .stream()
                .map(row -> new ImpayeDTO(
                        (String) row[0],
                        (Long) row[1],
                        (BigDecimal) row[2]))
                .collect(Collectors.toList());
    }

    // ── Prêts à risque ────────────────────────────────────────────────────────
    public List<PretEnCoursDTO> getPretsARisque(String codSoc, int seuilMois) {
        return pretPersRepository.findPretsArisques(codSoc, seuilMois)
                .stream()
                .map(p -> new PretEnCoursDTO(
                        p.getId().getMatPers(),
                        p.getCodServ(),
                        p.getCodGrad(),
                        p.getTypPret(),
                        p.getPrtMntGlb(),
                        p.getPrtMntRem(),
                        p.getPrtMntGlb().subtract(p.getPrtMntRem() != null ? p.getPrtMntRem() : BigDecimal.ZERO),
                        p.getRemMen(),
                        p.getPrtDatAcc(),
                        p.getPrtDatFin()
                ))
                .collect(Collectors.toList());
    }

    // ── Échéancier d'un prêt ─────────────────────────────────────────────────
    public List<EcheancierDTO> getEcheancier(
            String codSoc, String matPers, Long codPret) {
        return ligPretRepository.findEcheancierByPret(codSoc, matPers, codPret)
                .stream()
                .map(l -> new EcheancierDTO(
                        l.getId().getLPret(),
                        l.getMoisPretPrevu(),
                        l.getMoisPret(),
                        l.getMntPeriod(),
                        l.getMntInt(),
                        l.getCapRest(),
                        l.getImpaye(),
                        l.getValPret()))
                .collect(Collectors.toList());
    }

    // ── Capital restant par mois ──────────────────────────────────────────────
    public BigDecimal getCapitalRestantParMois(String codSoc, LocalDate mois) {
        BigDecimal cap = ligPretRepository.sumCapitalRestantByMois(codSoc, mois);
        return cap != null ? cap : BigDecimal.ZERO;
    }
}