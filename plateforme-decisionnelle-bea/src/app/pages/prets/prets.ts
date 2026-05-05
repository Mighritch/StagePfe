import { Component, OnInit, signal, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Chart, registerables } from 'chart.js';
import { forkJoin } from 'rxjs';
import {
  CreditAnalyticsService,
  VwEncoursParTypePret,
  VwPretsParService,
  VwTopEmprunteurs,
  VwPretsParObjet,
} from '../../services/credit-analytics';
import { ChartHelperService } from '../../services/chart-helper.service';
import { ExportService } from '../../services/export.service'; // Import ajouté

Chart.register(...registerables);

@Component({
  selector: 'app-prets',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prets.html',
  styleUrl: './prets.scss',
})
export class Prets implements OnInit, OnDestroy {

  // ── Signaux de données ──────────────────────────────────────────────
  encoursParType  = signal<VwEncoursParTypePret[]>([]);
  pretsParService = signal<VwPretsParService[]>([]);
  topEmprunteurs  = signal<VwTopEmprunteurs[]>([]);
  pretsParObjet   = signal<VwPretsParObjet[]>([]);

  // ── Filtres ──────────────────────────────────────────────────────────
  selectedAnnee    = signal<number>(new Date().getFullYear());
  selectedService  = signal<string>('');
  selectedTypePret = signal<string>('');

  loading = signal<boolean>(false);
  error   = signal<string | null>(null);

  // ── Références aux canvas ─────────────────────────────────────────────
  @ViewChild('encoursChart')        encoursChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('serviceChart')        serviceChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('objetChart')          objetChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('topEmprunteursChart') topEmprunteursChartRef!: ElementRef<HTMLCanvasElement>;

  private charts: Chart[] = [];

  constructor(
    private creditService: CreditAnalyticsService,
    private chartHelper: ChartHelperService,
    private exportService: ExportService // Injecté ici
  ) {}

  ngOnInit(): void {
    this.loadAllData();
  }

  ngOnDestroy(): void {
    this.destroyCharts();
  }

  loadAllData(): void {
    this.loading.set(true);
    this.error.set(null);
    this.destroyCharts();

    const annee    = this.selectedAnnee();
    const service  = this.selectedService()  || undefined;
    const typePret = this.selectedTypePret() || undefined;

    forkJoin({
      encours    : this.creditService.getEncoursParTypePret(typePret, service, annee),
      parService : this.creditService.getPretsParService(service, typePret, annee),
      parObjet   : this.creditService.getPretsParObjet(service, annee),
      emprunteurs: this.creditService.getTopEmprunteurs(service, annee),
    }).subscribe({
      next: ({ encours, parService, parObjet, emprunteurs }) => {
        this.encoursParType.set(encours);
        this.pretsParService.set(parService);
        this.pretsParObjet.set(parObjet);
        this.topEmprunteurs.set(emprunteurs);

        // On attend la mise à jour du template (Angular signals -> DOM)
        setTimeout(() => this.initCharts(), 0);
      },
      error: (err) => {
        console.error('Erreur chargement prêts :', err);
        this.error.set('Erreur lors du chargement des données.');
        this.loading.set(false);
      },
      complete: () => this.loading.set(false),
    });
  }

  onFiltreChange(): void {
    this.loadAllData();
  }

  // ── Initialisation des graphiques ───────────────────────────────────

  private initCharts(): void {
    // 1. Encours (Doughnut)
    if (this.encoursChartRef) {
      this.charts.push(
        new Chart(this.encoursChartRef.nativeElement, {
          type: 'doughnut',
          data: this.chartHelper.encoursParTypeChart(this.encoursParType()),
          options: this.chartHelper.doughnutOptions('Encours')
        })
      );
    }

    // 2. Prêts par Service (Barres verticales)
    if (this.serviceChartRef) {
      this.charts.push(
        new Chart(this.serviceChartRef.nativeElement, {
          type: 'bar',
          data: this.chartHelper.pretsParServiceChart(this.pretsParService()),
          options: this.chartHelper.barOptions('Montant (MAD)')
        })
      );
    }

    // 3. Prêts par Objet (Pie)
    if (this.objetChartRef) {
      this.charts.push(
        new Chart(this.objetChartRef.nativeElement, {
          type: 'pie',
          data: this.chartHelper.pretsParObjetChart(this.pretsParObjet()),
          options: this.chartHelper.pieOptions()
        })
      );
    }

    // 4. Top Emprunteurs (Barres horizontales)
    if (this.topEmprunteursChartRef) {
      this.charts.push(
        new Chart(this.topEmprunteursChartRef.nativeElement, {
          type: 'bar',
          data: this.chartHelper.topEmprunteursChart(this.topEmprunteurs()),
          options: this.chartHelper.barHorizontalOptions()
        })
      );
    }
  }

  private destroyCharts(): void {
    this.charts.forEach(chart => chart.destroy());
    this.charts = [];
  }

  formatMontant(value: number): string {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'MAD',
      maximumFractionDigits: 0,
    }).format(value);
  }

  // ====================== EXPORTS ======================

  exportPretsExcel(): void {
    this.exportService.exportMultipleSheets([
      { name: 'Encours_Par_Type', data: this.encoursParType() },
      { name: 'Prets_Par_Service', data: this.pretsParService() },
      { name: 'Prets_Par_Objet', data: this.pretsParObjet() },
      { name: 'Top_Emprunteurs', data: this.topEmprunteurs() }
    ], `Prets_${this.selectedAnnee()}`);
  }

  async exportPretsPDF(): Promise<void> {
    // Assurez-vous que l'id 'prets-content' existe dans votre fichier HTML
    await this.exportService.exportElementToPDF('prets-content', `Prets_${this.selectedAnnee()}`);
  }
}