package com.bea.bea_bi_backend.services;

import com.bea.bea_bi_backend.dto.*;
import com.bea.bea_bi_backend.entities.ParFixe;
import com.bea.bea_bi_backend.repositories.BulletinHRepository;
import com.bea.bea_bi_backend.repositories.ParFixeRepository;
import com.bea.bea_bi_backend.repositories.PossedeVhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasseSalarialeService {

    private final PossedeVhRepository possedeVhRepository;
    private final BulletinHRepository bulletinHRepository;
    private final ParFixeRepository parFixeRepository;

    // ── Masse salariale par mois ──────────────────────────────────────────────
    public List<MasseSalarialeParMoisDTO> getMasseSalarialeParMois(
            String codSoc, LocalDate debut, LocalDate fin) {
        return possedeVhRepository.sumMontantByMoisAndPeriode(codSoc, debut, fin)
                .stream()
                .map(row -> new MasseSalarialeParMoisDTO(
                        (LocalDate) row[0],
                        (BigDecimal) row[1]))
                .collect(Collectors.toList());
    }

    // ── Masse salariale par service ───────────────────────────────────────────
    public List<MasseSalarialeParServiceDTO> getMasseSalarialeParService(
            String codSoc, LocalDate debut, LocalDate fin) {
        return possedeVhRepository.sumMontantByServiceAndPeriode(codSoc, debut, fin)
                .stream()
                .map(row -> new MasseSalarialeParServiceDTO(
                        (String) row[0],
                        (BigDecimal) row[1]))
                .collect(Collectors.toList());
    }

    // ── Masse salariale par rubrique ──────────────────────────────────────────
    public List<RubriqueDTO> getMasseSalarialeParRubrique(
            String codSoc, String typePar, LocalDate debut, LocalDate fin) {
        return possedeVhRepository.sumMontantByRubriqueAndPeriode(codSoc, typePar, debut, fin)
                .stream()
                .map(row -> {
                    String abrv = (String) row[0];
                    BigDecimal montant = (BigDecimal) row[1];
                    // Récupération du libellé depuis ParFixe
                    return parFixeRepository.findById(abrv)
                            .map(p -> new RubriqueDTO(
                                    p.getAbrvFixe(),
                                    p.getLibFixe(),
                                    p.getTypePar(),
                                    p.getSensImput(),
                                    montant))
                            .orElse(new RubriqueDTO(abrv, abrv, typePar, null, montant));
                })
                .collect(Collectors.toList());
    }

    // ── Gains actifs ──────────────────────────────────────────────────────────
    public List<RubriqueDTO> getGainsActifs(String codSoc) {
        return parFixeRepository.findGainsActifs()
                .stream()
                .map(p -> new RubriqueDTO(
                        p.getAbrvFixe(),
                        p.getLibFixe(),
                        p.getTypePar(),
                        p.getSensImput(),
                        p.getValeur()))
                .collect(Collectors.toList());
    }

    // ── Retenues actives ──────────────────────────────────────────────────────
    public List<RubriqueDTO> getRetenuesActives(String codSoc) {
        return parFixeRepository.findRetenuesActives()
                .stream()
                .map(p -> new RubriqueDTO(
                        p.getAbrvFixe(),
                        p.getLibFixe(),
                        p.getTypePar(),
                        p.getSensImput(),
                        p.getValeur()))
                .collect(Collectors.toList());
    }

    // ── Présence par service ──────────────────────────────────────────────────
    public List<PresenceDTO> getPresenceParService(
            String codSoc, LocalDate debut, LocalDate fin) {
        return bulletinHRepository.sumPresenceByServiceAndPeriode(codSoc, debut, fin)
                .stream()
                .map(row -> new PresenceDTO(
                        (String) row[0],
                        (BigDecimal) row[1]))
                .collect(Collectors.toList());
    }

    // ── Présence par grade ────────────────────────────────────────────────────
    public List<PresenceDTO> getPresenceParGrade(
            String codSoc, LocalDate debut, LocalDate fin) {
        return bulletinHRepository.sumPresenceByGradeAndPeriode(codSoc, debut, fin)
                .stream()
                .map(row -> new PresenceDTO(
                        (String) row[0],
                        (BigDecimal) row[1]))
                .collect(Collectors.toList());
    }

    // ── Masse salariale totale ────────────────────────────────────────────────
    public BigDecimal getMasseSalarialeTotale(
            String codSoc, LocalDate debut, LocalDate fin) {
        BigDecimal total = possedeVhRepository
                .sumMasseSalarialeByPeriode(codSoc, debut, fin);
        return total != null ? total : BigDecimal.ZERO;
    }
}