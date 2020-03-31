import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicamentos } from 'app/shared/model/medicamentos.model';

type EntityResponseType = HttpResponse<IMedicamentos>;
type EntityArrayResponseType = HttpResponse<IMedicamentos[]>;

@Injectable({ providedIn: 'root' })
export class MedicamentosService {
  public resourceUrl = SERVER_API_URL + 'api/medicamentos';

  constructor(protected http: HttpClient) {}

  create(medicamentos: IMedicamentos): Observable<EntityResponseType> {
    return this.http.post<IMedicamentos>(this.resourceUrl, medicamentos, { observe: 'response' });
  }

  update(medicamentos: IMedicamentos): Observable<EntityResponseType> {
    return this.http.put<IMedicamentos>(this.resourceUrl, medicamentos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMedicamentos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMedicamentos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
