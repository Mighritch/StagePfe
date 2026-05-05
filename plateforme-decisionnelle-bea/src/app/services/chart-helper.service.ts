import { Injectable } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';

// Imports de tes modèles existants
import { VwEffectifParService, VwEffectifParGrade, VwEffectifParSexe, VwEffectifEvolution } from '../models/analytics';
import { VwAbsencesParAnnee, VwAbsencesParMotif, VwTopAbsencesEmploye, VwDureeMoyenneConge } from './absences-analytics';
import { VwEncoursParTypePret, VwPretsParService, VwPretsParObjet, VwTopEmprunteurs } from './credit-analytics';
import { VwMasseSalarialeService, VwSalaireParGrade, VwEvolutionMasseSalariale, VwTauxChargeService } from './charges-analytics';

@Injectable({ providedIn: 'root' })
export class ChartHelperService {

  // ─── COULEURS COMMUNES ───────────────────────────────────────────────
  private colors = {
    blue:   ['#378ADD', '#85B7EB', '#B5D4F4', '#E6F1FB'],
    teal:   ['#1D9E75', '#5DCAA5', '#9FE1CB', '#E1F5EE'],
    amber:  ['#BA7517', '#EF9F27', '#FAC775', '#FAEEDA'],
    coral:  ['#D85A30', '#F0997B', '#F5C4B3', '#FAECE7'],
    purple: ['#7F77DD', '#AFA9EC', '#CECBF6', '#EEEDFE'],
    multi:  ['#378ADD', '#1D9E75', '#BA7517', '#D85A30', '#7F77DD',
             '#85B7EB', '#5DCAA5', '#EF9F27', '#F0997B', '#AFA9EC'],
  };

  // ─── EFFECTIFS ───────────────────────────────────────────────────────

  effectifsParServiceChart(data: VwEffectifParService[]): ChartData<'bar'> {
    return {
      labels: data.map(d => d.libServ),
      datasets: [{
        label: 'Nb employés',
        data: data.map(d => d.nbEmployes),
        backgroundColor: this.colors.blue[0],
        borderColor: this.colors.blue[0],
        borderRadius: 4,
        borderWidth: 0,
      }]
    };
  }

  effectifsParGradeChart(data: VwEffectifParGrade[]): ChartData<'doughnut'> {
    return {
      labels: data.map(d => d.libGrad),
      datasets: [{
        data: data.map(d => d.nbEmployes),
        backgroundColor: this.colors.multi,
        borderWidth: 2,
      }]
    };
  }

  effectifsParSexeChart(data: VwEffectifParSexe[]): ChartData<'pie'> {
    return {
      labels: data.map(d => d.sexe === 'M' ? 'Masculin' : 'Féminin'),
      datasets: [{
        data: data.map(d => d.nbEmployes),
        backgroundColor: [this.colors.blue[0], this.colors.coral[0]],
        borderWidth: 2,
      }]
    };
  }

  effectifEvolutionChart(data: VwEffectifEvolution[]): ChartData<'line'> {
    return {
      labels: data.map(d => String(d.annee)),
      datasets: [{
        label: 'Effectif total',
        data: data.map(d => d.nbEmployes),
        borderColor: this.colors.blue[0],
        backgroundColor: this.colors.blue[3],
        fill: true,
        tension: 0.4,
        pointRadius: 5,
      }]
    };
  }

  // ─── ABSENCES ────────────────────────────────────────────────────────

  absencesParMotifChart(data: VwAbsencesParMotif[]): ChartData<'bar'> {
    return {
      labels: data.map(d => d.libMot),
      datasets: [{
        label: 'Total jours',
        data: data.map(d => d.totalJours),
        backgroundColor: this.colors.amber[0],
        borderRadius: 4,
        borderWidth: 0,
      }]
    };
  }

  absencesParAnneeChart(data: VwAbsencesParAnnee[]): ChartData<'line'> {
    return {
      labels: data.map(d => String(d.anneeCng)),
      datasets: [{
        label: 'Nb demandes',
        data: data.map(d => d.nbDemandes),
        borderColor: this.colors.teal[0],
        backgroundColor: this.colors.teal[3],
        fill: true,
        tension: 0.4,
        pointRadius: 5,
      }]
    };
  }

  topAbsencesChart(data: VwTopAbsencesEmploye[]): ChartData<'bar'> {
    return {
      labels: data.map(d => `${d.prenPers} ${d.nomPers}`),
      datasets: [{
        label: 'Jours absence',
        data: data.map(d => d.totalJoursAbsence),
        backgroundColor: this.colors.coral[0],
        borderRadius: 4,
        borderWidth: 0,
      }]
    };
  }

  dureeMoyenneCongeChart(data: VwDureeMoyenneConge[]): ChartData<'bar'> {
    return {
      labels: data.map(d => d.libMot),
      datasets: [
        {
          label: 'Durée moyenne',
          data: data.map(d => d.dureeMoyenne),
          backgroundColor: this.colors.amber[0],
          borderRadius: 4,
          borderWidth: 0,
        },
        {
          label: 'Durée max',
          data: data.map(d => d.dureeMax),
          backgroundColor: this.colors.amber[2],
          borderRadius: 4,
          borderWidth: 0,
        }
      ]
    };
  }

  // ─── PRÊTS ───────────────────────────────────────────────────────────

  encoursParTypeChart(data: VwEncoursParTypePret[]): ChartData<'doughnut'> {
    return {
      labels: data.map(d => d.libPret),
      datasets: [{
        data: data.map(d => d.encoursTotal),
        backgroundColor: this.colors.multi,
        borderWidth: 2,
      }]
    };
  }

  pretsParServiceChart(data: VwPretsParService[]): ChartData<'bar'> {
    return {
      labels: data.map(d => d.libServ),
      datasets: [{
        label: 'Crédit total',
        data: data.map(d => d.totalCredit),
        backgroundColor: this.colors.purple[0],
        borderRadius: 4,
        borderWidth: 0,
      }]
    };
  }

  pretsParObjetChart(data: VwPretsParObjet[]): ChartData<'pie'> {
    return {
      labels: data.map(d => d.objetPret),
      datasets: [{
        data: data.map(d => d.nbPrets),
        backgroundColor: this.colors.multi,
        borderWidth: 2,
      }]
    };
  }

  topEmprunteursChart(data: VwTopEmprunteurs[]): ChartData<'bar'> {
    return {
      labels: data.map(d => `${d.prenPers} ${d.nomPers}`),
      datasets: [{
        label: 'Total emprunté',
        data: data.map(d => d.totalEmprunte),
        backgroundColor: this.colors.teal[0],
        borderRadius: 4,
        borderWidth: 0,
      }]
    };
  }

  // ─── SALAIRES / CHARGES ──────────────────────────────────────────────

  masseSalarialeServiceChart(data: VwMasseSalarialeService[]): ChartData<'bar'> {
    return {
      labels: data.map(d => d.libServ),
      datasets: [
        {
          label: 'Masse salariale',
          data: data.map(d => d.masseSalariale),
          backgroundColor: this.colors.blue[0],
          borderRadius: 4,
          borderWidth: 0,
        },
        {
          label: 'Salaire moyen',
          data: data.map(d => d.salaireMoyen),
          backgroundColor: this.colors.blue[2],
          borderRadius: 4,
          borderWidth: 0,
        }
      ]
    };
  }

  salaireParGradeChart(data: VwSalaireParGrade[]): ChartData<'bar'> {
    return {
      labels: data.map(d => d.libGrad),
      datasets: [
        {
          label: 'Salaire moyen',
          data: data.map(d => d.salaireMoyen),
          backgroundColor: this.colors.teal[0],
          borderRadius: 4,
          borderWidth: 0,
        },
        {
          label: 'Salaire max',
          data: data.map(d => d.salaireMax),
          backgroundColor: this.colors.teal[2],
          borderRadius: 4,
          borderWidth: 0,
        }
      ]
    };
  }

  evolutionMasseSalarialeChart(data: VwEvolutionMasseSalariale[]): ChartData<'line'> {
    return {
      labels: data.map(d => String(d.annee)),
      datasets: [
        {
          label: 'Masse salariale',
          data: data.map(d => d.masseSalariale),
          borderColor: this.colors.blue[0],
          backgroundColor: this.colors.blue[3],
          fill: true,
          tension: 0.4,
          pointRadius: 5,
        },
        {
          label: 'Année précédente',
          data: data.map(d => d.massePrec),
          borderColor: this.colors.blue[2],
          backgroundColor: 'transparent',
          borderDash: [5, 5],
          tension: 0.4,
          pointRadius: 3,
        }
      ]
    };
  }

  tauxChargeServiceChart(data: VwTauxChargeService[]): ChartData<'bar'> {
    return {
      labels: data.map(d => d.libServ),
      datasets: [{
        label: 'Taux moyen (%)',
        data: data.map(d => d.tauxMoyen),
        backgroundColor: this.colors.amber[0],
        borderRadius: 4,
        borderWidth: 0,
      }]
    };
  }

  // ─── OPTIONS RÉUTILISABLES ───────────────────────────────────────────

  barOptions(yLabel = ''): ChartOptions<'bar'> {
    return {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { display: false },
        tooltip: { mode: 'index', intersect: false }
      },
      scales: {
        x: { grid: { display: false }, ticks: { maxRotation: 45 } },
        y: { beginAtZero: true, title: { display: !!yLabel, text: yLabel } }
      }
    };
  }

  barHorizontalOptions(): ChartOptions<'bar'> {
    return {
      responsive: true,
      maintainAspectRatio: false,
      indexAxis: 'y',   // ← rend le bar chart horizontal
      plugins: {
        legend: { display: false },
        tooltip: { mode: 'index', intersect: false }
      },
      scales: {
        x: { beginAtZero: true, grid: { display: false } },
        y: { grid: { display: false } }
      }
    };
  }

  barGroupedOptions(): ChartOptions<'bar'> {
    return {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { position: 'top' },
        tooltip: { mode: 'index', intersect: false }
      },
      scales: {
        x: { grid: { display: false }, ticks: { maxRotation: 45 } },
        y: { beginAtZero: true }
      }
    };
  }

  lineOptions(): ChartOptions<'line'> {
    return {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { position: 'top' },
        tooltip: { mode: 'index', intersect: false }
      },
      scales: {
        x: { grid: { display: false } },
        y: { beginAtZero: false }
      }
    };
  }

  pieOptions(): ChartOptions<'pie'> {
    return {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { position: 'right' },
        tooltip: { callbacks: {
          label: ctx => `${ctx.label}: ${ctx.parsed.toLocaleString('fr-FR')}`
        }}
      }
    };
  }

  doughnutOptions(centerLabel = ''): ChartOptions<'doughnut'> {
    return {
      responsive: true,
      maintainAspectRatio: false,
      cutout: '65%',
      plugins: {
        legend: { position: 'right' },
        tooltip: { callbacks: {
          label: ctx => `${ctx.label}: ${ctx.parsed.toLocaleString('fr-FR')}`
        }}
      }
    };
  }
}