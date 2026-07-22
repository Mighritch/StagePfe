import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, from, throwError } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';
import { KeycloakService } from 'keycloak-angular';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private keycloak: KeycloakService,
    private router: Router
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.includes('/assets')) {
      return next.handle(req);
    }

    return from(this.getValidToken()).pipe(
      switchMap(token => {
        if (token) {
          const authReq = req.clone({
            setHeaders: { Authorization: `Bearer ${token}` }
          });
          return next.handle(authReq);
        }
        return next.handle(req);
      }),
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.keycloak.login();
        }
        return throwError(() => error);
      })
    );
  }

  private async getValidToken(): Promise<string | null> {
    try {
      const loggedIn = await this.keycloak.isLoggedIn();
      if (!loggedIn) {
        return null;
      }
      await this.keycloak.updateToken(30);
      return await this.keycloak.getToken();
    } catch (error) {
      console.error('Erreur rafraîchissement token, redirection login...', error);
      this.keycloak.login();
      return null;
    }
  }
}