import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { BaseChartDirective } from 'ng2-charts';
import { ChartData, ChartOptions } from 'chart.js';

// Services et types
import { ChartHelperService } from '../../services/chart-helper.service';
import { AbsencesAnalyticsService, VwAbsencesParAnnee } from '../../services/absences-analytics';
import { EffectifAnalyticsService, VwEffectifParService } from '../../services/effectif-analytics';
import { CreditAnalyticsService, VwTopEmprunteurs } from '../../services/credit-analytics';
import { ChargesAnalyticsService, VwEvolutionMasseSalariale } from '../../services/charges-analytics';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {

  // ── Statistiques globales (Signaux) ───────────────────────────────────
  totalEmployes    = signal<number>(0);
  totalAbsences    = signal<number>(0);
  totalPrets       = signal<number>(0);
  masseSalariale   = signal<number>(0);
  tauxAbsenteisme  = signal<number>(0);

  // ── Données brutes pour traitement ────────────────────────────────────
  absencesParAnnee    = signal<VwAbsencesParAnnee[]>([]);
  effectifsParService = signal<VwEffectifParService[]>([]);
  topEmprunteurs      = signal<VwTopEmprunteurs[]>([]);
  evolutionMasse      = signal<VwEvolutionMasseSalariale[]>([]);

  // ── Configuration Chart.js ───────────────────────────────────────────
  absencesChartData = signal<ChartData<'line'> | null>(null);
  evolutionMasseChartData = signal<ChartData<'line'> | null>(null);

  barOptions!: ChartOptions<'bar'>;
  lineOptions!: ChartOptions<'line'>;

  // ── États de l'interface ──────────────────────────────────────────────
  loading     = signal<boolean>(true);
  error       = signal<string | null>(null);
  currentYear = new Date().getFullYear();

  constructor(
    private absencesService: AbsencesAnalyticsService,
    private effectifService: EffectifAnalyticsService,
    private creditService: CreditAnalyticsService,
    private chargesService: ChargesAnalyticsService,
    private chartHelper: ChartHelperService,
    private exportService: ExportService
  ) {
    this.barOptions  = this.chartHelper.barOptions();
    this.lineOptions = this.chartHelper.lineOptions();
  }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  private loadDashboardData(): void {
    this.loading.set(true);
    this.error.set(null);

    forkJoin({
      absences   : this.absencesService.getAbsencesParAnnee(this.currentYear),
      effectifs  : this.effectifService.getEffectifParService(undefined, undefined, this.currentYear),
      emprunteurs: this.creditService.getTopEmprunteurs(undefined, this.currentYear),
      evolution  : this.chargesService.getEvolutionMasseSalariale(),
    }).subscribe({
      next: ({ absences, effectifs, emprunteurs, evolution }) => {
        this.absencesChartData.set(this.chartHelper.absencesParAnneeChart(absences));
        this.evolutionMasseChartData.set(this.chartHelper.evolutionMasseSalarialeChart(evolution));

        this.absencesParAnnee.set(absences);
        this.effectifsParService.set(effectifs);
        this.evolutionMasse.set(evolution);
        this.topEmprunteurs.set(emprunteurs.slice(0, 5));

        this.totalAbsences.set(absences.reduce((sum, item) => sum + (item.nbDemandes || 0), 0));
        this.totalEmployes.set(effectifs.reduce((sum, item) => sum + (item.nbEmployes || 0), 0));
        this.totalPrets.set(emprunteurs.reduce((sum, item) => sum + (item.nbPrets || 0), 0));

        const currentData = evolution.find(item => item.annee === this.currentYear);
        if (currentData) {
          this.masseSalariale.set(currentData.masseSalariale);
          this.tauxAbsenteisme.set(currentData.variationPct || 0);
        }

        this.loading.set(false);
      },
      error: (err) => {
        console.error('Erreur chargement dashboard :', err);
        this.error.set('Erreur lors du chargement des données. Vérifiez votre connexion.');
        this.loading.set(false);
      }
    });
  }

  // ── Export Excel ─────────────────────────────────────────────────────
  exportDashboardExcel(): void {
    const fileName = `Dashboard_BEA_${new Date().toISOString().slice(0, 10)}`;
    this.exportService.exportMultipleSheets([
      {
        name: 'Statistiques',
        data: [{
          totalEmployes  : this.totalEmployes(),
          totalAbsences  : this.totalAbsences(),
          totalPrets     : this.totalPrets(),
          masseSalariale : this.masseSalariale()
        }]
      },
      { name: 'Absences',       data: this.absencesParAnnee() },
      { name: 'Effectifs',      data: this.effectifsParService() },
      { name: 'Top Emprunteurs', data: this.topEmprunteurs() },
      { name: 'Evolution Masse', data: this.evolutionMasse() }
    ], fileName);
  }

  // ── Export PDF ───────────────────────────────────────────────────────
  async exportDashboardPDF(): Promise<void> {
    const fileName = `Dashboard_BEA_${new Date().toISOString().slice(0, 10)}`;
    await this.exportService.exportElementToPDF(
      'dashboard-content',
      fileName,
      'Tableau de Bord — Vue Globale'
    );
  }

  // ── Utilitaires de formatage ──────────────────────────────────────────

  formatNombre(value: number): string {
    return new Intl.NumberFormat('fr-FR').format(value);
  }

  formatMontant(value: number): string {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'MAD',
    }).format(value);
  }

  getVariationClass(variation: number): string {
    if (variation > 0) return 'positive';
    if (variation < 0) return 'negative';
    return 'neutral';
  }
}