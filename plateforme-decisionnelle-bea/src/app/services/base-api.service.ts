import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BaseApiService {

  private readonly apiUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) {}

  /**
   * Méthode GET générique avec gestion propre des paramètres
   */
  get<T>(endpoint: string, params?: any): Observable<T> {
    let httpParams = new HttpParams();

    if (params) {
      Object.keys(params).forEach(key => {
        const value = params[key];
        if (value !== undefined && value !== null && value !== '') {
          httpParams = httpParams.set(key, value.toString());
        }
      });
    }

    // Nettoyage de l'endpoint (évite les doubles slashs)
    const cleanEndpoint = endpoint.startsWith('/') ? endpoint.substring(1) : endpoint;

    return this.http.get<T>(`${this.apiUrl}/${cleanEndpoint}`, {
      params: httpParams,
      withCredentials: true,           // Important pour les cookies / sessions
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      })
    });
  }

  /**
   * Méthode POST générique (au cas où vous en auriez besoin plus tard)
   */
  post<T>(endpoint: string, body: any, params?: any): Observable<T> {
    let httpParams = new HttpParams();

    if (params) {
      Object.keys(params).forEach(key => {
        const value = params[key];
        if (value !== undefined && value !== null && value !== '') {
          httpParams = httpParams.set(key, value.toString());
        }
      });
    }

    const cleanEndpoint = endpoint.startsWith('/') ? endpoint.substring(1) : endpoint;

    return this.http.post<T>(`${this.apiUrl}/${cleanEndpoint}`, body, {
      params: httpParams,
      withCredentials: true,
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      })
    });
  }
}