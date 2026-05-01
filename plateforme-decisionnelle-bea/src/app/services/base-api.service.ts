import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, timeout, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BaseApiService {
  // Base URL du backend Spring Boot (port 8081 selon application.properties)
  protected apiUrl = 'http://localhost:8081/api';
  
  // Timeout par défaut pour les requêtes (30 secondes)
  protected defaultTimeout = 30000;
  
  // Nombre de tentatives en cas d'échec
  protected retryCount = 1;

  constructor(protected http: HttpClient) {}

  /**
   * Méthode GET générique avec gestion des paramètres
   * @param endpoint - Point d'accès API (ex: 'absences/par-annee')
   * @param params - Paramètres de requête optionnels
   * @param headers - Headers HTTP optionnels
   * @returns Observable du type T
   */
  protected get<T>(
    endpoint: string, 
    params?: any, 
    headers?: HttpHeaders
  ): Observable<T> {
    let httpParams = new HttpParams();
    
    // Construction des paramètres de requête
    if (params) {
      Object.keys(params).forEach(key => {
        const value = params[key];
        if (value !== null && value !== undefined && value !== '') {
          // Gestion des tableaux (ex: pour les ids multiples)
          if (Array.isArray(value)) {
            value.forEach(item => {
              httpParams = httpParams.append(key, item);
            });
          } else {
            httpParams = httpParams.set(key, value.toString());
          }
        }
      });
    }

    // Headers par défaut
    let httpHeaders = headers || new HttpHeaders();
    httpHeaders = httpHeaders.set('Content-Type', 'application/json');
    httpHeaders = httpHeaders.set('Accept', 'application/json');

    return this.http.get<T>(`${this.apiUrl}/${endpoint}`, {
      params: httpParams,
      headers: httpHeaders
    }).pipe(
      timeout(this.defaultTimeout),
      retry(this.retryCount),
      catchError(this.handleError)
    );
  }

  /**
   * Méthode POST générique
   * @param endpoint - Point d'accès API
   * @param body - Corps de la requête
   * @param params - Paramètres de requête optionnels
   * @returns Observable du type T
   */
  protected post<T>(
    endpoint: string, 
    body: any, 
    params?: any
  ): Observable<T> {
    let httpParams = new HttpParams();
    
    if (params) {
      Object.keys(params).forEach(key => {
        const value = params[key];
        if (value !== null && value !== undefined) {
          httpParams = httpParams.set(key, value.toString());
        }
      });
    }

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json');

    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, body, {
      params: httpParams,
      headers: headers
    }).pipe(
      timeout(this.defaultTimeout),
      catchError(this.handleError)
    );
  }

  /**
   * Méthode PUT générique
   * @param endpoint - Point d'accès API
   * @param body - Corps de la requête
   * @param params - Paramètres de requête optionnels
   * @returns Observable du type T
   */
  protected put<T>(
    endpoint: string, 
    body: any, 
    params?: any
  ): Observable<T> {
    let httpParams = new HttpParams();
    
    if (params) {
      Object.keys(params).forEach(key => {
        const value = params[key];
        if (value !== null && value !== undefined) {
          httpParams = httpParams.set(key, value.toString());
        }
      });
    }

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json');

    return this.http.put<T>(`${this.apiUrl}/${endpoint}`, body, {
      params: httpParams,
      headers: headers
    }).pipe(
      timeout(this.defaultTimeout),
      catchError(this.handleError)
    );
  }

  /**
   * Méthode DELETE générique
   * @param endpoint - Point d'accès API
   * @param params - Paramètres de requête optionnels
   * @returns Observable du type T
   */
  protected delete<T>(
    endpoint: string, 
    params?: any
  ): Observable<T> {
    let httpParams = new HttpParams();
    
    if (params) {
      Object.keys(params).forEach(key => {
        const value = params[key];
        if (value !== null && value !== undefined) {
          httpParams = httpParams.set(key, value.toString());
        }
      });
    }

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json');

    return this.http.delete<T>(`${this.apiUrl}/${endpoint}`, {
      params: httpParams,
      headers: headers
    }).pipe(
      timeout(this.defaultTimeout),
      catchError(this.handleError)
    );
  }

  /**
   * Gestion centralisée des erreurs HTTP
   * @param error - Erreur retournée par HttpClient
   * @returns Observable d'erreur
   */
  private handleError(error: any): Observable<never> {
    let errorMessage = 'Une erreur inconnue est survenue';
    
    if (error.error instanceof ErrorEvent) {
      // Erreur côté client
      errorMessage = `Erreur client: ${error.error.message}`;
      console.error('Client Error:', error.error);
    } else {
      // Erreur côté serveur
      switch (error.status) {
        case 0:
          errorMessage = 'Impossible de se connecter au serveur. Vérifiez que le backend est démarré sur le port 8081.';
          break;
        case 400:
          errorMessage = 'Requête invalide (400)';
          break;
        case 401:
          errorMessage = 'Non authentifié (401) - Veuillez vous connecter via Keycloak';
          break;
        case 403:
          errorMessage = 'Accès non autorisé (403) - Vous n\'avez pas les droits nécessaires';
          break;
        case 404:
          errorMessage = 'Ressource non trouvée (404)';
          break;
        case 408:
          errorMessage = 'Délai d\'attente dépassé (408)';
          break;
        case 500:
          errorMessage = 'Erreur interne du serveur (500)';
          break;
        case 503:
          errorMessage = 'Service indisponible (503)';
          break;
        default:
          errorMessage = `Erreur serveur (${error.status}): ${error.message}`;
      }
      console.error('Server Error:', error);
    }
    
    // Logger l'erreur pour débogage
    console.error(`API Error [${new Date().toISOString()}]:`, {
      message: errorMessage,
      status: error.status,
      statusText: error.statusText,
      url: error.url
    });
    
    return throwError(() => new Error(errorMessage));
  }

  /**
   * Définit l'URL de base de l'API (utile pour les environnements multiples)
   * @param url - Nouvelle URL de base
   */
  setApiUrl(url: string): void {
    this.apiUrl = url;
  }

  /**
   * Récupère l'URL de base actuelle
   * @returns URL de base
   */
  getApiUrl(): string {
    return this.apiUrl;
  }

  /**
   * Définit le timeout par défaut
   * @param timeoutMs - Timeout en millisecondes
   */
  setTimeout(timeoutMs: number): void {
    this.defaultTimeout = timeoutMs;
  }

  /**
   * Définit le nombre de tentatives en cas d'échec
   * @param count - Nombre de tentatives
   */
  setRetryCount(count: number): void {
    this.retryCount = count;
  }
}