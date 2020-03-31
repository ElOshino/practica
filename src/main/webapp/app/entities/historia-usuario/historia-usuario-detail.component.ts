import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

@Component({
  selector: 'jhi-historia-usuario-detail',
  templateUrl: './historia-usuario-detail.component.html'
})
export class HistoriaUsuarioDetailComponent implements OnInit {
  historiaUsuario: IHistoriaUsuario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historiaUsuario }) => (this.historiaUsuario = historiaUsuario));
  }

  previousState(): void {
    window.history.back();
  }
}
