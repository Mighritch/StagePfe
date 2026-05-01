import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseApiService } from './base-api.service';

export interface VwAbsencesParMotif {
  libMot: string;
  typCng: string;
  abrvMot: string;
  libServ: string;
  annee: number;
  nbDemandes: number;
  totalJours: number;
}

export interface VwAbsenteismeService {
  libServ: string;
  annee: number;
  nbEmployesAbsents: number;
  totalJoursAbsence: number;
  moyenneJoursParEmploye: number;
}

export interface VwSoldeCongeType {
  typCng: string;
  soldeMoyen: number;
  initialMoyen: number;
  prisMoyen: number;
  cumulMoyen: number;
  libServ: string;
  annee: number;
}

export interface VwAbsencesParAnnee {
  anneeCng: number;
  typCng: string;
  libServ: string;
  nbDemandes: number;
  totalJours: number;
}

export interface VwTopAbsencesEmploye {
  matPers: string;
  nomPers: string;
  prenPers: string;
  totalJoursAbsence: number;
  nbDemandes: number;
  libServ: string;
  annee: number;
}

export interface VwDureeMoyenneConge {
  libMot: string;
  dureeMoyenne: number;
  dureeMin: number;
  dureeMax: number;
  libServ: string;
  annee: number;
}

@Injectable({
  providedIn: 'root'
})
export class AbsencesAnalyticsService {
  private readonly baseEndpoint = 'analytics/absences';

  constructor(@Inject(BaseApiService) private baseApi: BaseApiService) {}

  getAbsencesParMotif(
    service?: string,
    annee?: number,
    motif?: string
  ): Observable<VwAbsencesParMotif[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    if (motif) params.motif = motif;
    return this.baseApi.get<VwAbsencesParMotif[]>(`${this.baseEndpoint}/par-motif`, params);
  }

  getAbsenteismeService(
    service?: string,
    annee?: number
  ): Observable<VwAbsenteismeService[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwAbsenteismeService[]>(`${this.baseEndpoint}/absenteisme-service`, params);
  }

  getSoldeCongeType(
    typCng?: string,
    annee?: number
  ): Observable<VwSoldeCongeType[]> {
    const params: any = {};
    if (typCng) params.typCng = typCng;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwSoldeCongeType[]>(`${this.baseEndpoint}/solde-conge-type`, params);
  }

  getAbsencesParAnnee(
    annee?: number,
    typCng?: string,
    service?: string
  ): Observable<VwAbsencesParAnnee[]> {
    const params: any = {};
    if (annee) params.annee = annee;
    if (typCng) params.typCng = typCng;
    if (service) params.service = service;
    return this.baseApi.get<VwAbsencesParAnnee[]>(`${this.baseEndpoint}/par-annee`, params);
  }

  getTopAbsencesEmploye(
    service?: string,
    annee?: number
  ): Observable<VwTopAbsencesEmploye[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwTopAbsencesEmploye[]>(`${this.baseEndpoint}/top-absences-employe`, params);
  }

  getDureeMoyenneConge(
    service?: string,
    annee?: number,
    motif?: string
  ): Observable<VwDureeMoyenneConge[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    if (motif) params.motif = motif;
    return this.baseApi.get<VwDureeMoyenneConge[]>(`${this.baseEndpoint}/duree-moyenne-conge`, params);
  }
}