import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseApiService } from './base-api.service';

// ── Import et ré-export des interfaces ───────────────────────────────────────
import {
  VwEffectifParService,
  VwEffectifParGrade,
  VwEffectifParSexe,
  VwEffectifEvolution,
  VwEffectifParAdmtech,
  VwEffectifServiceGrade,
  VwEffectifParAffectation,
} from '../models/analytics';

export type {
  VwEffectifParService,
  VwEffectifParGrade,
  VwEffectifParSexe,
  VwEffectifEvolution,
  VwEffectifParAdmtech,
  VwEffectifServiceGrade,
  VwEffectifParAffectation,
};

// ── Service ───────────────────────────────────────────────────────────────────

@Injectable({
  providedIn: 'root',
})
export class EffectifAnalyticsService {
  private readonly baseEndpoint = 'analytics/effectif';

  // @Inject supprimé — inutile pour une dépendance de type class
  constructor(private baseApi: BaseApiService) {}

  getEffectifParService(
    service?: string,
    grade?: string,
    annee?: number
  ): Observable<VwEffectifParService[]> {
    const params: any = {};
    if (service)                            params.service = service;
    if (grade)                              params.grade   = grade;
    if (annee !== undefined && annee !== null) params.annee = annee;
    return this.baseApi.get<VwEffectifParService[]>(`${this.baseEndpoint}/par-service`, params);
  }

  getEffectifParGrade(
    grade?: string,
    service?: string,
    annee?: number
  ): Observable<VwEffectifParGrade[]> {
    const params: any = {};
    if (grade)                              params.grade   = grade;
    if (service)                            params.service = service;
    if (annee !== undefined && annee !== null) params.annee = annee;
    return this.baseApi.get<VwEffectifParGrade[]>(`${this.baseEndpoint}/par-grade`, params);
  }

  getEffectifParSexe(
    service?: string,
    grade?: string,
    annee?: number
  ): Observable<VwEffectifParSexe[]> {
    const params: any = {};
    if (service)                            params.service = service;
    if (grade)                              params.grade   = grade;
    if (annee !== undefined && annee !== null) params.annee = annee;
    return this.baseApi.get<VwEffectifParSexe[]>(`${this.baseEndpoint}/par-sexe`, params);
  }

  getEffectifEvolution(
    service?: string,
    grade?: string,
    annee?: number
  ): Observable<VwEffectifEvolution[]> {
    const params: any = {};
    if (service)                            params.service = service;
    if (grade)                              params.grade   = grade;
    if (annee !== undefined && annee !== null) params.annee = annee;
    return this.baseApi.get<VwEffectifEvolution[]>(`${this.baseEndpoint}/evolution`, params);
  }

  getEffectifParAdmtech(
    service?: string,
    annee?: number
  ): Observable<VwEffectifParAdmtech[]> {
    const params: any = {};
    if (service)                            params.service = service;
    if (annee !== undefined && annee !== null) params.annee = annee;
    return this.baseApi.get<VwEffectifParAdmtech[]>(`${this.baseEndpoint}/par-admtech`, params);
  }

  getEffectifServiceGrade(
    service?: string,
    grade?: string,
    annee?: number
  ): Observable<VwEffectifServiceGrade[]> {
    const params: any = {};
    if (service)                            params.service = service;
    if (grade)                              params.grade   = grade;
    if (annee !== undefined && annee !== null) params.annee = annee;
    return this.baseApi.get<VwEffectifServiceGrade[]>(`${this.baseEndpoint}/service-grade`, params);
  }

  getEffectifParAffectation(
    service?: string,
    annee?: number
  ): Observable<VwEffectifParAffectation[]> {
    const params: any = {};
    if (service)                            params.service = service;
    if (annee !== undefined && annee !== null) params.annee = annee;
    return this.baseApi.get<VwEffectifParAffectation[]>(`${this.baseEndpoint}/par-affectation`, params);
  }
}