import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExamenFisico } from 'app/shared/model/examen-fisico.model';

type EntityResponseType = HttpResponse<IExamenFisico>;
type EntityArrayResponseType = HttpResponse<IExamenFisico[]>;

@Injectable({ providedIn: 'root' })
export class ExamenFisicoService {
  public resourceUrl = SERVER_API_URL + 'api/examen-fisicos';

  constructor(protected http: HttpClient) {}

  create(examenFisico: IExamenFisico): Observable<EntityResponseType> {
    return this.http.post<IExamenFisico>(this.resourceUrl, examenFisico, { observe: 'response' });
  }

  update(examenFisico: IExamenFisico): Observable<EntityResponseType> {
    return this.http.put<IExamenFisico>(this.resourceUrl, examenFisico, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExamenFisico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExamenFisico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
