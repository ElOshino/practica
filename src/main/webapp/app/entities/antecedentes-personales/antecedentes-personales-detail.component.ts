import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';

@Component({
  selector: 'jhi-antecedentes-personales-detail',
  templateUrl: './antecedentes-personales-detail.component.html'
})
export class AntecedentesPersonalesDetailComponent implements OnInit {
  antecedentesPersonales: IAntecedentesPersonales | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antecedentesPersonales }) => (this.antecedentesPersonales = antecedentesPersonales));
  }

  previousState(): void {
    window.history.back();
  }
}
