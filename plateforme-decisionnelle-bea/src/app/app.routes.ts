import { Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { Absences } from './pages/absences/absences';
import { Effectifs } from './pages/effectifs/effectifs';
import { Prets } from './pages/prets/prets';
import { Salaires } from './pages/salaires/salaires';

export const routes: Routes = [
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    { path: 'dashboard', component: Dashboard },
    { path: 'absences', component: Absences },
    { path: 'effectifs', component: Effectifs },
    { path: 'prets', component: Prets },
    { path: 'salaires', component: Salaires },
    { path: '**', redirectTo: '/dashboard' }
];