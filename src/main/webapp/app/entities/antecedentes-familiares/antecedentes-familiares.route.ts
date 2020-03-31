import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAntecedentesFamiliares, AntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from './antecedentes-familiares.service';
import { AntecedentesFamiliaresComponent } from './antecedentes-familiares.component';
import { AntecedentesFamiliaresDetailComponent } from './antecedentes-familiares-detail.component';
import { AntecedentesFamiliaresUpdateComponent } from './antecedentes-familiares-update.component';

@Injectable({ providedIn: 'root' })
export class AntecedentesFamiliaresResolve implements Resolve<IAntecedentesFamiliares> {
  constructor(private service: AntecedentesFamiliaresService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAntecedentesFamiliares> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((antecedentesFamiliares: HttpResponse<AntecedentesFamiliares>) => {
          if (antecedentesFamiliares.body) {
            return of(antecedentesFamiliares.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AntecedentesFamiliares());
  }
}

export const antecedentesFamiliaresRoute: Routes = [
  {
    path: '',
    component: AntecedentesFamiliaresComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.antecedentesFamiliares.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AntecedentesFamiliaresDetailComponent,
    resolve: {
      antecedentesFamiliares: AntecedentesFamiliaresResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.antecedentesFamiliares.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AntecedentesFamiliaresUpdateComponent,
    resolve: {
      antecedentesFamiliares: AntecedentesFamiliaresResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.antecedentesFamiliares.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AntecedentesFamiliaresUpdateComponent,
    resolve: {
      antecedentesFamiliares: AntecedentesFamiliaresResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.antecedentesFamiliares.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
