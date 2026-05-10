import { Component, OnInit, signal, ViewChild, ElementRef, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Chart, registerables } from 'chart.js';
import { forkJoin } from 'rxjs';
import {
  ChargesAnalyticsService,
  VwMasseSalarialeService,
  VwSalaireParGrade,
  VwEvolutionMasseSalariale,
  VwTauxChargeService
} from '../../services/charges-analytics';
import { ChartHelperService } from '../../services/chart-helper.service';
import { ExportService } from '../../services/export.service';

Chart.register(...registerables);

@Component({
  selector: 'app-salaires',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './salaires.html',
  styleUrl: './salaires.scss',
})
export class Salaires implements OnInit {

  // ── Services ────────────────────────────────────────────────────────
  private chartHelper    = inject(ChartHelperService);
  private chargesService = inject(ChargesAnalyticsService);
  private exportService  = inject(ExportService);

  // ── Données (Signals) ───────────────────────────────────────────────
  masseSalarialeService = signal<VwMasseSalarialeService[]>([]);
  salaireParGrade       = signal<VwSalaireParGrade[]>([]);
  evolutionMasse        = signal<VwEvolutionMasseSalariale[]>([]);
  tauxChargeService     = signal<VwTauxChargeService[]>([]);

  // ── Filtres ──────────────────────────────────────────────────────────
  selectedAnnee   = signal<number>(new Date().getFullYear());
  selectedService = signal<string>('');
  selectedGrade   = signal<string>('');

  loading = signal<boolean>(false);
  error   = signal<string | null>(null);

  // ── Références aux canvas ─────────────────────────────────────────────
  @ViewChild('masseChart')     masseChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('gradeChart')     gradeChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('evolutionChart') evolutionChartRef!: ElementRef<HTMLCanvasElement>;

  private charts: Chart[] = [];

  ngOnInit(): void {
    this.loadAllData();
  }

  loadAllData(): void {
    this.loading.set(true);
    this.error.set(null);
    this.destroyCharts();

    const annee   = this.selectedAnnee();
    const service = this.selectedService() || undefined;
    const grade   = this.selectedGrade()   || undefined;

    forkJoin({
      masse    : this.chargesService.getMasseSalarialeService(service, annee),
      salaire  : this.chargesService.getSalaireParGrade(grade, service, annee),
      evolution: this.chargesService.getEvolutionMasseSalariale(annee),
      taux     : this.chargesService.getTauxChargeService(service, annee),
    }).subscribe({
      next: ({ masse, salaire, evolution, taux }) => {
        this.masseSalarialeService.set(masse);
        this.salaireParGrade.set(salaire);
        this.evolutionMasse.set(
          evolution.map(item => ({ ...item, variationPct: item.variationPct ?? 0 }))
        );
        this.tauxChargeService.set(taux);

        setTimeout(() => this.createCharts(), 0);
      },
      error: (err) => {
        console.error('Erreur chargement salaires :', err);
        this.error.set('Erreur lors du chargement des données.');
        this.loading.set(false);
      },
      complete: () => this.loading.set(false),
    });
  }

  // ── Gestion des Graphiques ──────────────────────────────────────────

  private createCharts(): void {
    this.destroyCharts();

    if (this.masseChartRef) {
      this.charts.push(new Chart(this.masseChartRef.nativeElement, {
        type: 'bar',
        data: this.chartHelper.masseSalarialeServiceChart(this.masseSalarialeService()),
        options: this.chartHelper.barGroupedOptions()
      }));
    }

    if (this.gradeChartRef) {
      this.charts.push(new Chart(this.gradeChartRef.nativeElement, {
        type: 'bar',
        data: this.chartHelper.salaireParGradeChart(this.salaireParGrade()),
        options: this.chartHelper.barGroupedOptions()
      }));
    }

    if (this.evolutionChartRef) {
      this.charts.push(new Chart(this.evolutionChartRef.nativeElement, {
        type: 'line',
        data: this.chartHelper.evolutionMasseSalarialeChart(this.evolutionMasse()),
        options: this.chartHelper.lineOptions()
      }));
    }
  }

  private destroyCharts(): void {
    if (this.charts.length > 0) {
      this.charts.forEach(chart => chart.destroy());
      this.charts = [];
    }
  }

  // ── Actions utilisateur ─────────────────────────────────────────────

  onFiltreChange(): void {
    this.loadAllData();
  }

  formatMontant(value: number): string {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'MAD',
      maximumFractionDigits: 0
    }).format(value);
  }

  // ── Exportation ─────────────────────────────────────────────────────

  exportSalairesExcel(): void {
    this.exportService.exportMultipleSheets([
      { name: 'Masse_Par_Service', data: this.masseSalarialeService() },
      { name: 'Salaire_Par_Grade', data: this.salaireParGrade() },
      { name: 'Evolution_Masse',   data: this.evolutionMasse() },
      { name: 'Taux_Charge',       data: this.tauxChargeService() }
    ], `Salaires_${this.selectedAnnee()}`);
  }

  async exportSalairesPDF(): Promise<void> {
    await this.exportService.exportElementToPDF(
      'salaires-content',
      `Salaires_${this.selectedAnnee()}`,
      `Analyse des Salaires — ${this.selectedAnnee()}`
    );
  }
}