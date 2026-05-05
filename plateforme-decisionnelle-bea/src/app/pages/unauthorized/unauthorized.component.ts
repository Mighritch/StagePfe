import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-unauthorized',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="unauthorized-container">
      <div class="unauthorized-card">
        <div class="error-icon">🔒</div>
        <h1>Accès non autorisé</h1>
        <p>Vous n'avez pas les droits nécessaires pour accéder à cette page.</p>
        <p class="error-details">Veuillez contacter votre administrateur si vous pensez qu'il s'agit d'une erreur.</p>
        <div class="button-group">
          <button (click)="goBack()" class="btn-primary">Retour à l'accueil</button>
          <button (click)="logout()" class="btn-secondary">Se déconnecter</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .unauthorized-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 60vh;
      padding: 20px;
    }
    .unauthorized-card {
      text-align: center;
      padding: 48px;
      background: white;
      border-radius: 16px;
      box-shadow: 0 10px 40px rgba(0,0,0,0.1);
      max-width: 500px;
      width: 100%;
    }
    .error-icon {
      font-size: 64px;
      margin-bottom: 20px;
    }
    h1 {
      color: #1a2a6c;
      margin-bottom: 16px;
      font-size: 28px;
    }
    p {
      color: #666;
      margin-bottom: 24px;
      line-height: 1.5;
    }
    .error-details {
      font-size: 14px;
      color: #999;
    }
    .button-group {
      display: flex;
      gap: 16px;
      justify-content: center;
      margin-top: 32px;
    }
    .btn-primary {
      background: linear-gradient(135deg, #1a2a6c, #b21f1f);
      color: white;
      border: none;
      padding: 12px 24px;
      border-radius: 8px;
      cursor: pointer;
      font-size: 14px;
      font-weight: 500;
      transition: transform 0.2s, box-shadow 0.2s;
    }
    .btn-primary:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0,0,0,0.2);
    }
    .btn-secondary {
      background: #f5f5f5;
      color: #666;
      border: 1px solid #ddd;
      padding: 12px 24px;
      border-radius: 8px;
      cursor: pointer;
      font-size: 14px;
      font-weight: 500;
      transition: all 0.2s;
    }
    .btn-secondary:hover {
      background: #e0e0e0;
    }
  `]
})
export class UnauthorizedComponent {
  constructor(private router: Router) {}
  
  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
  
  logout(): void {
    // Si tu as injecté KeycloakService
    // const keycloak = inject(KeycloakService);
    // keycloak.logout(window.location.origin);
    
    // Version simple pour l'instant
    this.router.navigate(['/dashboard']);
  }
}