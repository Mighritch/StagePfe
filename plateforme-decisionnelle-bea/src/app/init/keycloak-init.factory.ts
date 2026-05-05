import { KeycloakService } from 'keycloak-angular';

export function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8180', // L'URL de ton serveur Keycloak
        realm: 'bea-realm',           // Le nom de ton Realm
        clientId: 'angular-app'       // L'ID que tu as créé sur l'image
      },
      initOptions: {
        onLoad: 'check-sso', // Vérifie la session sans forcer le login immédiatement
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      }
    });
}