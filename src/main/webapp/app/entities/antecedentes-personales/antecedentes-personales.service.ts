import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';

type EntityResponseType = HttpResponse<IAntecedentesPersonales>;
type EntityArrayResponseType = HttpResponse<IAntecedentesPersonales[]>;

@Injectable({ providedIn: 'root' })
export class AntecedentesPersonalesService {
  public resourceUrl = SERVER_API_URL + 'api/antecedentes-personales';

  constructor(protected http: HttpClient) {}

  create(antecedentesPersonales: IAntecedentesPersonales): Observable<EntityResponseType> {
    return this.http.post<IAntecedentesPersonales>(this.resourceUrl, antecedentesPersonales, { observe: 'response' });
  }

  update(antecedentesPersonales: IAntecedentesPersonales): Observable<EntityResponseType> {
    return this.http.put<IAntecedentesPersonales>(this.resourceUrl, antecedentesPersonales, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAntecedentesPersonales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAntecedentesPersonales[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
