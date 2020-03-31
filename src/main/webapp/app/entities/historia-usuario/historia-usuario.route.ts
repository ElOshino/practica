import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHistoriaUsuario, HistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from './historia-usuario.service';
import { HistoriaUsuarioComponent } from './historia-usuario.component';
import { HistoriaUsuarioDetailComponent } from './historia-usuario-detail.component';
import { HistoriaUsuarioUpdateComponent } from './historia-usuario-update.component';

@Injectable({ providedIn: 'root' })
export class HistoriaUsuarioResolve implements Resolve<IHistoriaUsuario> {
  constructor(private service: HistoriaUsuarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistoriaUsuario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((historiaUsuario: HttpResponse<HistoriaUsuario>) => {
          if (historiaUsuario.body) {
            return of(historiaUsuario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HistoriaUsuario());
  }
}

export const historiaUsuarioRoute: Routes = [
  {
    path: '',
    component: HistoriaUsuarioComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HistoriaUsuarioDetailComponent,
    resolve: {
      historiaUsuario: HistoriaUsuarioResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HistoriaUsuarioUpdateComponent,
    resolve: {
      historiaUsuario: HistoriaUsuarioResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HistoriaUsuarioUpdateComponent,
    resolve: {
      historiaUsuario: HistoriaUsuarioResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'practicaApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
