import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMedicamentos, Medicamentos } from 'app/shared/model/medicamentos.model';
import { MedicamentosService } from './medicamentos.service';
import { MedicamentosComponent } from './medicamentos.component';
import { MedicamentosDetailComponent } from './medicamentos-detail.component';
import { MedicamentosUpdateComponent } from './medicamentos-update.component';

@Injectable({ providedIn: 'root' })
export class MedicamentosResolve implements Resolve<IMedicamentos> {
  constructor(private service: MedicamentosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicamentos> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((medicamentos: HttpResponse<Medicamentos>) => {
          if (medicamentos.body) {
            return of(medicamentos.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Medicamentos());
  }
}

export const medicamentosRoute: Routes = [
  {
    path: '',
    component: MedicamentosComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.medicamentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicamentosDetailComponent,
    resolve: {
      medicamentos: MedicamentosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.medicamentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicamentosUpdateComponent,
    resolve: {
      medicamentos: MedicamentosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.medicamentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicamentosUpdateComponent,
    resolve: {
      medicamentos: MedicamentosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.medicamentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
