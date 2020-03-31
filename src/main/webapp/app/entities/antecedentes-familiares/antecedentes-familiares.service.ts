import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';

type EntityResponseType = HttpResponse<IAntecedentesFamiliares>;
type EntityArrayResponseType = HttpResponse<IAntecedentesFamiliares[]>;

@Injectable({ providedIn: 'root' })
export class AntecedentesFamiliaresService {
  public resourceUrl = SERVER_API_URL + 'api/antecedentes-familiares';

  constructor(protected http: HttpClient) {}

  create(antecedentesFamiliares: IAntecedentesFamiliares): Observable<EntityResponseType> {
    return this.http.post<IAntecedentesFamiliares>(this.resourceUrl, antecedentesFamiliares, { observe: 'response' });
  }

  update(antecedentesFamiliares: IAntecedentesFamiliares): Observable<EntityResponseType> {
    return this.http.put<IAntecedentesFamiliares>(this.resourceUrl, antecedentesFamiliares, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAntecedentesFamiliares>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAntecedentesFamiliares[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
