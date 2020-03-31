import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamenFisico } from 'app/shared/model/examen-fisico.model';

@Component({
  selector: 'jhi-examen-fisico-detail',
  templateUrl: './examen-fisico-detail.component.html'
})
export class ExamenFisicoDetailComponent implements OnInit {
  examenFisico: IExamenFisico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examenFisico }) => (this.examenFisico = examenFisico));
  }

  previousState(): void {
    window.history.back();
  }
}
