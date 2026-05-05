import {
  ApplicationConfig,
  APP_INITIALIZER,
  importProvidersFrom,
  provideBrowserGlobalErrorListeners,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts';

import { routes } from './app.routes';

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8180',
        realm: 'bea-realm',
        clientId: 'angular-app',
      },
      initOptions: {
        // ✅ CORRECTION PRINCIPALE :
        // 'login-required' force un redirect vers Keycloak au démarrage,
        // ce qui provoque un double montage des composants (et donc des
        // requêtes dupliquées). On passe à 'check-sso' qui utilise une
        // iframe silencieuse sans recharger l'app.
        onLoad: 'check-sso',

        // ✅ Ce fichier doit exister dans src/assets/silent-check-sso.html
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',

        // ✅ Désactive les vérifications iframe qui causent aussi des
        // rechargements parasites dans certains navigateurs
        checkLoginIframe: false,
      },
      enableBearerInterceptor: true,
      bearerPrefix: 'Bearer',
      bearerExcludedUrls: ['/assets'],
    });
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    importProvidersFrom(KeycloakAngularModule),
    provideCharts(withDefaultRegisterables()),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
    KeycloakService,
  ],
};