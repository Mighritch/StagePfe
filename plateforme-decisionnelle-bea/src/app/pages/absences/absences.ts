import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { BaseChartDirective } from 'ng2-charts';
import { ChartData, ChartOptions } from 'chart.js';

import { ChartHelperService } from '../../services/chart-helper.service';
import { ExportService } from '../../services/export.service';
import {
  AbsencesAnalyticsService,
  VwAbsencesParAnnee,
  VwAbsencesParMotif,
  VwTopAbsencesEmploye,
  VwDureeMoyenneConge
} from '../../services/absences-analytics';

@Component({
  selector: 'app-absences',
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective],
  templateUrl: './absences.html',
  styleUrl: './absences.scss',
})
export class Absences implements OnInit {

  // ── Données brutes (Signaux) ──────────────────────────────────────────
  absencesParAnnee  = signal<VwAbsencesParAnnee[]>([]);
  absencesParMotif  = signal<VwAbsencesParMotif[]>([]);
  topAbsences       = signal<VwTopAbsencesEmploye[]>([]);
  dureeMoyenneConge = signal<VwDureeMoyenneConge[]>([]);

  // ── Signaux pour les graphiques Chart.js ──────────────────────────────
  motifChartData        = signal<ChartData<'bar'> | null>(null);
  topAbsencesChartData  = signal<ChartData<'bar'> | null>(null);
  dureeMoyenneChartData = signal<ChartData<'bar'> | null>(null);

  // ── Options des graphiques ───────────────────────────────────────────
  barOptions!: ChartOptions<'bar'>;
  horizontalOptions!: ChartOptions<'bar'>;

  // ── Filtres et état ──────────────────────────────────────────────────
  selectedAnnee     = signal<number>(new Date().getFullYear());
  selectedService   = signal<string>('');
  selectedMotif     = signal<string>('');
  availableYears    = signal<number[]>([2022, 2023, 2024, 2025]);
  availableServices = signal<string[]>([]);
  availableMotifs   = signal<string[]>([]);

  loading = signal<boolean>(false);
  error   = signal<string | null>(null);

  // ── Signal calculé pour fusionner les données motif avec durée moyenne
  motifWithDuration = computed(() => {
    const motifs = this.absencesParMotif();
    const durees = this.dureeMoyenneConge();
    return motifs.map((motif, index) => ({
      ...motif,
      dureeMoyenne: durees[index]?.dureeMoyenne || 0
    }));
  });

  constructor(
    private absencesService: AbsencesAnalyticsService,
    private chartHelper: ChartHelperService,
    private exportService: ExportService
  ) {
    this.barOptions       = this.chartHelper.barOptions('Jours');
    this.horizontalOptions = this.chartHelper.barHorizontalOptions();
  }

  ngOnInit(): void {
    this.loadAllData();
  }

  loadAllData(): void {
    this.loading.set(true);
    this.error.set(null);

    const annee   = this.selectedAnnee();
    const service = this.selectedService() || undefined;
    const motif   = this.selectedMotif()   || undefined;

    forkJoin({
      parAnnee : this.absencesService.getAbsencesParAnnee(annee, undefined, service),
      parMotif : this.absencesService.getAbsencesParMotif(service, annee, motif),
      top      : this.absencesService.getTopAbsencesEmploye(service, annee),
      duree    : this.absencesService.getDureeMoyenneConge(service, annee, motif),
    }).subscribe({
      next: ({ parAnnee, parMotif, top, duree }) => {
        this.absencesParAnnee.set(parAnnee);
        this.absencesParMotif.set(parMotif);
        this.topAbsences.set(top);
        this.dureeMoyenneConge.set(duree);

        this.availableMotifs.set([...new Set(parMotif.map(m => m.libMot))]);

        this.motifChartData.set(this.chartHelper.absencesParMotifChart(parMotif));
        this.topAbsencesChartData.set(this.chartHelper.topAbsencesChart(top));
        this.dureeMoyenneChartData.set(this.chartHelper.dureeMoyenneCongeChart(duree));

        this.loading.set(false);
      },
      error: (err) => {
        console.error('Erreur chargement absences :', err);
        this.error.set('Erreur lors du chargement des données. Veuillez vérifier votre connexion ou votre authentification.');
        this.loading.set(false);
      },
      complete: () => this.loading.set(false),
    });
  }

  onFiltreChange(): void {
    this.loadAllData();
  }

  formatJours(jours: number): string {
    return (jours || 0).toFixed(2) + ' jours';
  }

  getDureeMoyenneForMotif(index: number): number {
    const durees = this.dureeMoyenneConge();
    return durees[index]?.dureeMoyenne || 0;
  }

  // ── EXPORTS ──────────────────────────────────────────────────────────

  exportAbsencesExcel(): void {
    this.exportService.exportMultipleSheets([
      { name: 'Absences_Par_Motif',  data: this.absencesParMotif() },
      { name: 'Top_Absences',        data: this.topAbsences() },
      { name: 'Historique_Annee',    data: this.absencesParAnnee() },
      { name: 'Duree_Moyenne',       data: this.motifWithDuration() }
    ], `Absences_${this.selectedAnnee()}`);
  }

  async exportAbsencesPDF(): Promise<void> {
    await this.exportService.exportElementToPDF(
      'absences-content',
      `Absences_${this.selectedAnnee()}`,
      `Analyse des Absences — ${this.selectedAnnee()}`
    );
  }
}