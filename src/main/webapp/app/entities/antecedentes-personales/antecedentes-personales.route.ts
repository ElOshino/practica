import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAntecedentesPersonales, AntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from './antecedentes-personales.service';
import { AntecedentesPersonalesComponent } from './antecedentes-personales.component';
import { AntecedentesPersonalesDetailComponent } from './antecedentes-personales-detail.component';
import { AntecedentesPersonalesUpdateComponent } from './antecedentes-personales-update.component';

@Injectable({ providedIn: 'root' })
export class AntecedentesPersonalesResolve implements Resolve<IAntecedentesPersonales> {
  constructor(private service: AntecedentesPersonalesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAntecedentesPersonales> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((antecedentesPersonales: HttpResponse<AntecedentesPersonales>) => {
          if (antecedentesPersonales.body) {
            return of(antecedentesPersonales.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AntecedentesPersonales());
  }
}

export const antecedentesPersonalesRoute: Routes = [
  {
    path: '',
    component: AntecedentesPersonalesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.antecedentesPersonales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AntecedentesPersonalesDetailComponent,
    resolve: {
      antecedentesPersonales: AntecedentesPersonalesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.antecedentesPersonales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AntecedentesPersonalesUpdateComponent,
    resolve: {
      antecedentesPersonales: AntecedentesPersonalesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.antecedentesPersonales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AntecedentesPersonalesUpdateComponent,
    resolve: {
      antecedentesPersonales: AntecedentesPersonalesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.antecedentesPersonales.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
