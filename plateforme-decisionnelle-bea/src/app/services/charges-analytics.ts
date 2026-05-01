import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseApiService } from './base-api.service';

export interface VwMasseSalarialeMois {
  mois: Date;
  masseSalariale: number;
  nbEmployes: number;
  libServ: string;
  annee: number;
  moisNum: number;
}

export interface VwChargesParTypeBulletin {
  codTypBul: string;
  abrvFixe: string;
  totalCharges: number;
  moyenneCharge: number;
  nbBulletins: number;
  libServ: string;
  annee: number;
}

export interface VwMasseSalarialeService {
  libServ: string;
  masseSalariale: number;
  salaireMoyen: number;
  nbEmployes: number;
  annee: number;
}

export interface VwSalaireParGrade {
  libGrad: string;
  salaireMoyen: number;
  salaireMin: number;
  salaireMax: number;
  totalCharges: number;
  libServ: string;
  annee: number;
}

export interface VwEvolutionMasseSalariale {
  annee: number;
  masseSalariale: number;
  massePrec: number;
  variationPct: number;
}

export interface VwTauxChargeService {
  libServ: string;
  tauxMoyen: number;
  tauxMax: number;
  tauxMin: number;
  annee: number;
}

@Injectable({
  providedIn: 'root'
})
export class ChargesAnalyticsService {
  private readonly baseEndpoint = 'analytics/charges';

  constructor(@Inject(BaseApiService) private baseApi: BaseApiService) {}

  getMasseSalarialeMois(
    service?: string,
    annee?: number,
    mois?: number
  ): Observable<VwMasseSalarialeMois[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    if (mois) params.mois = mois;
    return this.baseApi.get<VwMasseSalarialeMois[]>(`${this.baseEndpoint}/masse-salariale-mois`, params);
  }

  getChargesParTypeBulletin(
    service?: string,
    annee?: number
  ): Observable<VwChargesParTypeBulletin[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwChargesParTypeBulletin[]>(`${this.baseEndpoint}/par-type-bulletin`, params);
  }

  getMasseSalarialeService(
    service?: string,
    annee?: number
  ): Observable<VwMasseSalarialeService[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwMasseSalarialeService[]>(`${this.baseEndpoint}/masse-salariale-service`, params);
  }

  getSalaireParGrade(
    grade?: string,
    service?: string,
    annee?: number
  ): Observable<VwSalaireParGrade[]> {
    const params: any = {};
    if (grade) params.grade = grade;
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwSalaireParGrade[]>(`${this.baseEndpoint}/salaire-par-grade`, params);
  }

  getEvolutionMasseSalariale(
    annee?: number
  ): Observable<VwEvolutionMasseSalariale[]> {
    const params: any = {};
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEvolutionMasseSalariale[]>(`${this.baseEndpoint}/evolution-masse-salariale`, params);
  }

  getTauxChargeService(
    service?: string,
    annee?: number
  ): Observable<VwTauxChargeService[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwTauxChargeService[]>(`${this.baseEndpoint}/taux-charge-service`, params);
  }
}