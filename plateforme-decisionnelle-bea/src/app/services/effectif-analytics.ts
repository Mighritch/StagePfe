import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseApiService } from './base-api.service';

export interface VwEffectifParService {
  libServ: string;
  typeServ: string;
  nbEmployes: number;
  libGrad: string;
  annee: number;
}

export interface VwEffectifParGrade {
  libGrad: string;
  codCateg: string;
  nbEmployes: number;
  libServ: string;
  annee: number;
}

export interface VwEffectifParAdmtech {
  libAdmTech: string;
  codCat: string;
  nbEmployes: number;
  libServ: string;
  annee: number;
}

export interface VwEffectifParSexe {
  sexe: string;
  nbEmployes: number;
  pourcentage: number;
  libServ: string;
  libGrad: string;
  annee: number;
}

export interface VwEffectifEvolution {
  mois: Date;
  nbEmployes: number;
  libServ: string;
  libGrad: string;
  annee: number;
}

export interface VwEffectifServiceGrade {
  libServ: string;
  libGrad: string;
  nbEmployes: number;
  annee: number;
}

export interface VwEffectifParAffectation {
  libAffect: string;
  regime: string;
  jours: number;
  heures: number;
  nbEmployes: number;
  libServ: string;
  annee: number;
}

@Injectable({
  providedIn: 'root'
})
export class EffectifAnalyticsService {
  private readonly baseEndpoint = 'analytics/effectif';

  constructor(@Inject(BaseApiService) private baseApi: BaseApiService) {}

  getEffectifParService(
    service?: string,
    grade?: string,
    annee?: number
  ): Observable<VwEffectifParService[]> {
    const params: any = {};
    if (service) params.service = service;
    if (grade) params.grade = grade;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEffectifParService[]>(`${this.baseEndpoint}/par-service`, params);
  }

  getEffectifParGrade(
    grade?: string,
    service?: string,
    annee?: number
  ): Observable<VwEffectifParGrade[]> {
    const params: any = {};
    if (grade) params.grade = grade;
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEffectifParGrade[]>(`${this.baseEndpoint}/par-grade`, params);
  }

  getEffectifParAdmtech(
    service?: string,
    annee?: number
  ): Observable<VwEffectifParAdmtech[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEffectifParAdmtech[]>(`${this.baseEndpoint}/par-admtech`, params);
  }

  getEffectifParSexe(
    service?: string,
    grade?: string,
    annee?: number
  ): Observable<VwEffectifParSexe[]> {
    const params: any = {};
    if (service) params.service = service;
    if (grade) params.grade = grade;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEffectifParSexe[]>(`${this.baseEndpoint}/par-sexe`, params);
  }

  getEffectifEvolution(
    service?: string,
    grade?: string,
    annee?: number
  ): Observable<VwEffectifEvolution[]> {
    const params: any = {};
    if (service) params.service = service;
    if (grade) params.grade = grade;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEffectifEvolution[]>(`${this.baseEndpoint}/evolution`, params);
  }

  getEffectifServiceGrade(
    service?: string,
    grade?: string,
    annee?: number
  ): Observable<VwEffectifServiceGrade[]> {
    const params: any = {};
    if (service) params.service = service;
    if (grade) params.grade = grade;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEffectifServiceGrade[]>(`${this.baseEndpoint}/service-grade`, params);
  }

  getEffectifParAffectation(
    service?: string,
    annee?: number
  ): Observable<VwEffectifParAffectation[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEffectifParAffectation[]>(`${this.baseEndpoint}/par-affectation`, params);
  }
}