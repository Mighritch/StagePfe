import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('Plateforme Décisionnelle BEA');
  
  protected readonly menuItems = signal([
    { path: '/dashboard', label: 'Dashboard', icon: 'dashboard' },
    { path: '/absences', label: 'Absences', icon: 'event_busy' },
    { path: '/effectifs', label: 'Effectifs', icon: 'people' },
    { path: '/prets', label: 'Prêts', icon: 'account_balance' },
    { path: '/salaires', label: 'Salaires', icon: 'attach_money' }
  ]);
}