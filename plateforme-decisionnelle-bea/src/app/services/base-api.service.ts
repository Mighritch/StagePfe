// base-api.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, from, switchMap } from 'rxjs';
import { KeycloakService } from 'keycloak-angular';
import { environment } from '../../environments/Environment';

@Injectable({ providedIn: 'root' })
export class BaseApiService {
  private readonly apiUrl = `${environment.apiUrl}/api`;

  constructor(
    private http: HttpClient,
    private keycloak: KeycloakService
  ) {}

  get<T>(endpoint: string, params?: any): Observable<T> {
    const cleanEndpoint = endpoint.startsWith('/') ? endpoint.substring(1) : endpoint;
    let httpParams = new HttpParams();

    if (params) {
      Object.keys(params).forEach(key => {
        const value = params[key];
        if (value !== undefined && value !== null && value !== '') {
          httpParams = httpParams.set(key, value.toString());
        }
      });
    }

    return from(this.keycloak.getToken()).pipe(
      switchMap(token =>
        this.http.get<T>(`${this.apiUrl}/${cleanEndpoint}`, {
          params: httpParams,
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          }
        })
      )
    );
  }

  post<T>(endpoint: string, body: any, params?: any): Observable<T> {
    const cleanEndpoint = endpoint.startsWith('/') ? endpoint.substring(1) : endpoint;
    let httpParams = new HttpParams();

    if (params) {
      Object.keys(params).forEach(key => {
        const value = params[key];
        if (value !== undefined && value !== null && value !== '') {
          httpParams = httpParams.set(key, value.toString());
        }
      });
    }

    return from(this.keycloak.getToken()).pipe(
      switchMap(token =>
        this.http.post<T>(`${this.apiUrl}/${cleanEndpoint}`, body, {
          params: httpParams,
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          }
        })
      )
    );
  }
}