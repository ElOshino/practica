import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISignosVitales } from 'app/shared/model/signos-vitales.model';

type EntityResponseType = HttpResponse<ISignosVitales>;
type EntityArrayResponseType = HttpResponse<ISignosVitales[]>;

@Injectable({ providedIn: 'root' })
export class SignosVitalesService {
  public resourceUrl = SERVER_API_URL + 'api/signos-vitales';

  constructor(protected http: HttpClient) {}

  create(signosVitales: ISignosVitales): Observable<EntityResponseType> {
    return this.http.post<ISignosVitales>(this.resourceUrl, signosVitales, { observe: 'response' });
  }

  update(signosVitales: ISignosVitales): Observable<EntityResponseType> {
    return this.http.put<ISignosVitales>(this.resourceUrl, signosVitales, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISignosVitales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISignosVitales[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
