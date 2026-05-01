import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseApiService } from './base-api.service';

export interface VwEncoursParTypePret {
  libPret: string;
  typPret: string;
  codGrpPret: string;
  nbPrets: number;
  encoursTotal: number;
  montantMoyen: number;
  libServ: string;
  annee: number;
}

export interface VwTauxInteretPret {
  libPret: string;
  tauxMoyen: number;
  tauxMin: number;
  tauxMax: number;
  tauxReference: number;
  libServ: string;
  annee: number;
}

export interface VwPretsParService {
  libServ: string;
  nbPrets: number;
  totalCredit: number;
  montantMoyen: number;
  typPret: string;
  annee: number;
}

export interface VwCapitalRestantPret {
  libPret: string;
  capitalRestant: number;
  totalEcheances: number;
  totalInterets: number;
  annee: number;
}

export interface VwPretsParDuree {
  dureeRemb: number;
  nbPrets: number;
  totalMontant: number;
  moyenneMontant: number;
  libServ: string;
  annee: number;
}

export interface VwPretsParObjet {
  objetPret: string;
  nbPrets: number;
  totalMontant: number;
  tauxMoyen: number;
  dureeMoyenne: number;
  libServ: string;
  annee: number;
}

export interface VwTopEmprunteurs {
  matPers: string;
  nomPers: string;
  prenPers: string;
  nbPrets: number;
  totalEmprunte: number;
  tauxMoyen: number;
  libServ: string;
  annee: number;
}

@Injectable({
  providedIn: 'root'
})
export class CreditAnalyticsService {
  private readonly baseEndpoint = 'analytics/credit';

  constructor(@Inject(BaseApiService) private baseApi: BaseApiService) {}

  getEncoursParTypePret(
    typPret?: string,
    service?: string,
    annee?: number
  ): Observable<VwEncoursParTypePret[]> {
    const params: any = {};
    if (typPret) params.typPret = typPret;
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwEncoursParTypePret[]>(`${this.baseEndpoint}/encours-par-type-pret`, params);
  }

  getTauxInteretPret(
    typPret?: string,
    annee?: number
  ): Observable<VwTauxInteretPret[]> {
    const params: any = {};
    if (typPret) params.typPret = typPret;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwTauxInteretPret[]>(`${this.baseEndpoint}/taux-interet-pret`, params);
  }

  getPretsParService(
    service?: string,
    typPret?: string,
    annee?: number
  ): Observable<VwPretsParService[]> {
    const params: any = {};
    if (service) params.service = service;
    if (typPret) params.typPret = typPret;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwPretsParService[]>(`${this.baseEndpoint}/prets-par-service`, params);
  }

  getCapitalRestantPret(
    typPret?: string,
    annee?: number
  ): Observable<VwCapitalRestantPret[]> {
    const params: any = {};
    if (typPret) params.typPret = typPret;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwCapitalRestantPret[]>(`${this.baseEndpoint}/capital-restant-pret`, params);
  }

  getPretsParDuree(
    service?: string,
    annee?: number,
    duree?: number
  ): Observable<VwPretsParDuree[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    if (duree) params.duree = duree;
    return this.baseApi.get<VwPretsParDuree[]>(`${this.baseEndpoint}/prets-par-duree`, params);
  }

  getPretsParObjet(
    service?: string,
    annee?: number,
    objet?: string
  ): Observable<VwPretsParObjet[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    if (objet) params.objet = objet;
    return this.baseApi.get<VwPretsParObjet[]>(`${this.baseEndpoint}/prets-par-objet`, params);
  }

  getTopEmprunteurs(
    service?: string,
    annee?: number
  ): Observable<VwTopEmprunteurs[]> {
    const params: any = {};
    if (service) params.service = service;
    if (annee) params.annee = annee;
    return this.baseApi.get<VwTopEmprunteurs[]>(`${this.baseEndpoint}/top-emprunteurs`, params);
  }
}