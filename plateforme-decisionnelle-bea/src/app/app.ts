import { Component, signal, OnInit, inject } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { KeycloakService } from 'keycloak-angular';

interface MenuItem {
  path: string;
  label: string;
  icon: string;
  roles: string[];
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  // Injections
  private keycloak = inject(KeycloakService);
  private router = inject(Router);
  
  // Signals
  protected readonly title = signal('Plateforme Décisionnelle BEA');
  protected username = signal<string>('');
  protected menuItems = signal<MenuItem[]>([]);
  
  // Configuration du menu
  private readonly allMenuItems: MenuItem[] = [
    { path: '/dashboard', label: 'Dashboard', icon: 'dashboard', roles: ['RH', 'FINANCE', 'DIRECTION'] },
    { path: '/absences',  label: 'Absences',  icon: 'event_busy',   roles: ['RH', 'DIRECTION'] },
    { path: '/effectifs', label: 'Effectifs', icon: 'people',        roles: ['RH', 'DIRECTION'] },
    { path: '/prets',     label: 'Prêts',     icon: 'account_balance', roles: ['FINANCE', 'DIRECTION'] },
    { path: '/salaires',  label: 'Salaires',  icon: 'attach_money',  roles: ['RH', 'DIRECTION'] }
  ];
  
  async ngOnInit() {
    const isLoggedIn = await this.keycloak.isLoggedIn();
    
    if (isLoggedIn) {
      // 1. Récupérer et formater le nom de l'utilisateur
      try {
        const userProfile = await this.keycloak.loadUserProfile();
        const fullName = `${userProfile.firstName} ${userProfile.lastName}`.trim();
        this.username.set(fullName || userProfile.username || 'Utilisateur');
      } catch (error) {
        console.error('Erreur lors du chargement du profil:', error);
        this.username.set('Administrateur');
      }

      // 2. Filtrer le menu
      this.filterMenuByRoles();
      
      // Debug roles
      console.log('🎭 Menu - Rôles détectés:', this.keycloak.getUserRoles());
    }
  }
  
  private filterMenuByRoles(): void {
    const userRoles = this.keycloak.getUserRoles();
    
    console.log('Filtrage du menu avec rôles:', userRoles);
    
    const accessibleItems = this.allMenuItems.filter(item =>
      item.roles.some(role => userRoles.includes(role))
    );
    
    console.log('Items accessibles:', accessibleItems.map(i => i.label));
    this.menuItems.set(accessibleItems);
  }

  // ✅ Méthode de déconnexion avec Fallback
  async logout(): Promise<void> {
    try {
      // Déconnexion standard via le service
      await this.keycloak.logout(window.location.origin);
    } catch (error) {
      console.error('Erreur lors de la déconnexion via le service :', error);
      // Fallback : redirection manuelle si le service échoue
      const logoutUrl = `http://localhost:8180/realms/bea-realm/protocol/openid-connect/logout?redirect_uri=${encodeURIComponent(window.location.origin)}`;
      window.location.href = logoutUrl;
    }
  }
}