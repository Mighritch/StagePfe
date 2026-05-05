import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { Dashboard } from './pages/dashboard/dashboard';
import { Absences } from './pages/absences/absences';
import { Effectifs } from './pages/effectifs/effectifs';
import { Prets } from './pages/prets/prets';
import { Salaires } from './pages/salaires/salaires';
import { UnauthorizedComponent } from './pages/unauthorized/unauthorized.component';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },

  // Dashboard : Tous les rôles
  { 
    path: 'dashboard', 
    component: Dashboard, 
    canActivate: [authGuard],
    data: { roles: ['RH', 'FINANCE', 'DIRECTION'] }
  },

  // RH uniquement
  { 
    path: 'absences', 
    component: Absences, 
    canActivate: [authGuard],
    data: { roles: ['RH', 'DIRECTION'] }
  },
  
  { 
    path: 'effectifs', 
    component: Effectifs, 
    canActivate: [authGuard],
    data: { roles: ['RH', 'DIRECTION'] }
  },
  
  { 
    path: 'salaires', 
    component: Salaires, 
    canActivate: [authGuard],
    data: { roles: ['RH', 'DIRECTION'] }
  },

  // Finance uniquement
  { 
    path: 'prets', 
    component: Prets, 
    canActivate: [authGuard],
    data: { roles: ['FINANCE', 'DIRECTION'] }
  },

  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: '**', redirectTo: '/dashboard' },
];