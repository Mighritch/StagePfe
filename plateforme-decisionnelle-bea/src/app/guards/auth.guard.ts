import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

export const authGuard: CanActivateFn = async (route: ActivatedRouteSnapshot) => {
  const keycloak = inject(KeycloakService);
  const router = inject(Router);

  try {
    const isLoggedIn = await keycloak.isLoggedIn();

    if (!isLoggedIn) {
      await keycloak.login({
        redirectUri: window.location.origin + router.url,
      });
      return false;
    }

    const userRoles = keycloak.getUserRoles();
    console.log('🎭 Rôles utilisateur:', userRoles);

    // Rôles autorisés pour le dashboard
    const allowedRoles = ['DIRECTION', 'RH', 'FINANCE'];

    // Vérifier si l'utilisateur a au moins un des rôles autorisés
    const hasAllowedRole = allowedRoles.some(role => userRoles.includes(role));

    if (!hasAllowedRole) {
      console.warn(`⛔ Accès refusé - Rôles requis: ${allowedRoles}`);
      router.navigate(['/unauthorized']);
      return false;
    }

    console.log(`✅ Accès autorisé pour rôle(s): ${userRoles.filter(r => allowedRoles.includes(r))}`);
    return true;

  } catch (error) {
    console.error('Erreur auth guard :', error);
    router.navigate(['/login']);
    return false;
  }
};