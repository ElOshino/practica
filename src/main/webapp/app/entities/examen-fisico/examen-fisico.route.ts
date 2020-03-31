import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExamenFisico, ExamenFisico } from 'app/shared/model/examen-fisico.model';
import { ExamenFisicoService } from './examen-fisico.service';
import { ExamenFisicoComponent } from './examen-fisico.component';
import { ExamenFisicoDetailComponent } from './examen-fisico-detail.component';
import { ExamenFisicoUpdateComponent } from './examen-fisico-update.component';

@Injectable({ providedIn: 'root' })
export class ExamenFisicoResolve implements Resolve<IExamenFisico> {
  constructor(private service: ExamenFisicoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamenFisico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((examenFisico: HttpResponse<ExamenFisico>) => {
          if (examenFisico.body) {
            return of(examenFisico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExamenFisico());
  }
}

export const examenFisicoRoute: Routes = [
  {
    path: '',
    component: ExamenFisicoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.examenFisico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExamenFisicoDetailComponent,
    resolve: {
      examenFisico: ExamenFisicoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.examenFisico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExamenFisicoUpdateComponent,
    resolve: {
      examenFisico: ExamenFisicoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.examenFisico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExamenFisicoUpdateComponent,
    resolve: {
      examenFisico: ExamenFisicoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.examenFisico.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
