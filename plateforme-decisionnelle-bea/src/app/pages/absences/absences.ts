import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { BaseChartDirective } from 'ng2-charts';
import { ChartData, ChartOptions } from 'chart.js';

// Services et types
import { ChartHelperService } from '../../services/chart-helper.service';
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
  absencesParAnnee    = signal<VwAbsencesParAnnee[]>([]);
  absencesParMotif    = signal<VwAbsencesParMotif[]>([]);
  topAbsences         = signal<VwTopAbsencesEmploye[]>([]);
  dureeMoyenneConge   = signal<VwDureeMoyenneConge[]>([]);

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

  // Computed signal pour fusionner les données motif avec durée moyenne
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
    private chartHelper: ChartHelperService
  ) {
    // Initialisation des options via le helper
    this.barOptions = this.chartHelper.barOptions('Jours');
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
        // 1. Mise à jour des données brutes pour les tableaux
        this.absencesParAnnee.set(parAnnee);
        this.absencesParMotif.set(parMotif);
        this.topAbsences.set(top);
        this.dureeMoyenneConge.set(duree);

        // 2. Mise à jour des listes de filtres dynamiques
        this.availableMotifs.set([...new Set(parMotif.map(m => m.libMot))]);

        // 3. Préparation des données pour les graphiques via le helper
        this.motifChartData.set(this.chartHelper.absencesParMotifChart(parMotif));
        this.topAbsencesChartData.set(this.chartHelper.topAbsencesChart(top));
        this.dureeMoyenneChartData.set(this.chartHelper.dureeMoyenneCongeChart(duree));

        this.loading.set(false);
      },
      error: (err) => {
        console.error('Erreur chargement absences :', err);
        this.error.set('Erreur lors du chargement des données. Vérifiez votre authentification.');
        this.loading.set(false);
      },
      complete: () => this.loading.set(false),
    });
  }

  onFiltreChange(): void {
    this.loadAllData();
  }

  formatJours(jours: number): string {
    return jours.toFixed(2) + ' jours';
  }

  getDureeMoyenneForMotif(index: number): number {
    const durees = this.dureeMoyenneConge();
    return durees[index]?.dureeMoyenne || 0;
  }
}