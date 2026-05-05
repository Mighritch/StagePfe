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

    // ✅ Récupérer les rôles via Keycloak
    const userRoles = keycloak.getUserRoles();
    console.log('🎭 Rôles utilisateur:', userRoles); // Devrait afficher ['RH', 'default-roles-bea-realm']

    const requiredRoles = route.data['roles'] as string[];
    
    if (!requiredRoles || requiredRoles.length === 0) {
      return true;
    }

    // ✅ Vérifier si l'utilisateur a au moins un des rôles requis
    const hasRequiredRole = requiredRoles.some(role => userRoles.includes(role));

    if (!hasRequiredRole) {
      console.warn(`⛔ Accès refusé à ${route.url} - Rôles requis:`, requiredRoles);
      router.navigate(['/unauthorized']);
      return false;
    }

    console.log(`✅ Accès autorisé à ${route.url}`);
    return true;
  } catch (error) {
    console.error('Erreur auth guard :', error);
    return false;
  }
};