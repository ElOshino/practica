import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISignosVitales, SignosVitales } from 'app/shared/model/signos-vitales.model';
import { SignosVitalesService } from './signos-vitales.service';
import { SignosVitalesComponent } from './signos-vitales.component';
import { SignosVitalesDetailComponent } from './signos-vitales-detail.component';
import { SignosVitalesUpdateComponent } from './signos-vitales-update.component';

@Injectable({ providedIn: 'root' })
export class SignosVitalesResolve implements Resolve<ISignosVitales> {
  constructor(private service: SignosVitalesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISignosVitales> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((signosVitales: HttpResponse<SignosVitales>) => {
          if (signosVitales.body) {
            return of(signosVitales.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SignosVitales());
  }
}

export const signosVitalesRoute: Routes = [
  {
    path: '',
    component: SignosVitalesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.signosVitales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SignosVitalesDetailComponent,
    resolve: {
      signosVitales: SignosVitalesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.signosVitales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SignosVitalesUpdateComponent,
    resolve: {
      signosVitales: SignosVitalesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.signosVitales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SignosVitalesUpdateComponent,
    resolve: {
      signosVitales: SignosVitalesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.signosVitales.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
