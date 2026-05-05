import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BaseChartDirective } from 'ng2-charts';
import { forkJoin } from 'rxjs';

import { EffectifAnalyticsService } from '../../services/effectif-analytics';
import { ChartHelperService } from '../../services/chart-helper.service';
import { ExportService } from '../../services/export.service';
import {
  VwEffectifParService,
  VwEffectifParGrade,
  VwEffectifParSexe,
  VwEffectifEvolution,
} from '../../models/analytics';

@Component({
  selector: 'app-effectifs',
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective],
  templateUrl: './effectifs.html',
  styleUrl: './effectifs.scss',
})
export class Effectifs implements OnInit {

  // ── Données ──────────────────────────────────────────────────────────
  effectifsParService = signal<VwEffectifParService[]>([]);
  effectifsParGrade   = signal<VwEffectifParGrade[]>([]);
  effectifsParSexe    = signal<VwEffectifParSexe[]>([]);
  effectifEvolution   = signal<VwEffectifEvolution[]>([]);

  // ── Filtres ──────────────────────────────────────────────────────────
  selectedAnnee   = signal<number>(new Date().getFullYear());
  selectedService = signal<string>('');
  selectedGrade   = signal<string>('');

  loading = signal<boolean>(false);
  error   = signal<string | null>(null);

  // ── Services ─────────────────────────────────────────────────────────
  chartHelper     = inject(ChartHelperService);
  private effectifService = inject(EffectifAnalyticsService);
  private exportService   = inject(ExportService);

  // ── Charts computed ──────────────────────────────────────────────────
  chartService   = computed(() => this.chartHelper.effectifsParServiceChart(this.effectifsParService()));
  chartGrade     = computed(() => this.chartHelper.effectifsParGradeChart(this.effectifsParGrade()));
  chartSexe      = computed(() => this.chartHelper.effectifsParSexeChart(this.effectifsParSexe()));
  chartEvolution = computed(() => this.chartHelper.effectifEvolutionChart(this.effectifEvolution()));

  // ── Options exposées au template ─────────────────────────────────────
  barOpts      = this.chartHelper.barOptions('Nb employés');
  doughnutOpts = this.chartHelper.doughnutOptions();
  pieOpts      = this.chartHelper.pieOptions();
  lineOpts     = this.chartHelper.lineOptions();

  ngOnInit(): void {
    this.loadAllData();
  }

  loadAllData(): void {
    this.loading.set(true);
    this.error.set(null);

    // Réinitialiser les données avant le chargement
    this.effectifsParService.set([]);
    this.effectifsParGrade.set([]);
    this.effectifsParSexe.set([]);
    this.effectifEvolution.set([]);

    const annee   = this.selectedAnnee();
    const service = this.selectedService() || undefined;
    const grade   = this.selectedGrade()   || undefined;

    forkJoin({
      parService: this.effectifService.getEffectifParService(service, grade, annee),
      parGrade  : this.effectifService.getEffectifParGrade(grade, service, annee),
      parSexe   : this.effectifService.getEffectifParSexe(service, grade, annee),
      evolution : this.effectifService.getEffectifEvolution(service, grade, annee),
    }).subscribe({
      next: ({ parService, parGrade, parSexe, evolution }) => {
        this.effectifsParService.set(parService);
        this.effectifsParGrade.set(parGrade);
        this.effectifsParSexe.set(parSexe);
        this.effectifEvolution.set(evolution);
      },
      error: (err) => {
        console.error('Erreur chargement effectifs :', err);
        this.error.set('Erreur lors du chargement des données. Vérifiez votre authentification.');
        this.loading.set(false);
      },
      complete: () => this.loading.set(false),
    });
  }

  onFiltreChange(): void {
    this.loadAllData();
  }

  getTotalEmployes(): number {
    return this.effectifsParService().reduce((sum, item) => sum + (item.nbEmployes || 0), 0);
  }

  // ── Méthodes d'Exportation ───────────────────────────────────────────

  exportEffectifsExcel(): void {
    this.exportService.exportMultipleSheets([
      { name: 'Effectifs_Par_Service', data: this.effectifsParService() },
      { name: 'Effectifs_Par_Grade', data: this.effectifsParGrade() },
      { name: 'Effectifs_Par_Sexe', data: this.effectifsParSexe() },
      { name: 'Evolution', data: this.effectifEvolution() }
    ], `Effectifs_${this.selectedAnnee()}`);
  }

  async exportEffectifsPDF(): Promise<void> {
    // Assure-toi que l'ID 'effectifs-content' existe dans ton template HTML
    await this.exportService.exportElementToPDF('effectifs-content', `Effectifs_${this.selectedAnnee()}`);
  }
}