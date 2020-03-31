import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISignosVitales } from 'app/shared/model/signos-vitales.model';

@Component({
  selector: 'jhi-signos-vitales-detail',
  templateUrl: './signos-vitales-detail.component.html'
})
export class SignosVitalesDetailComponent implements OnInit {
  signosVitales: ISignosVitales | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signosVitales }) => (this.signosVitales = signosVitales));
  }

  previousState(): void {
    window.history.back();
  }
}
