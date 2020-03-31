import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';

@Component({
  selector: 'jhi-antecedentes-familiares-detail',
  templateUrl: './antecedentes-familiares-detail.component.html'
})
export class AntecedentesFamiliaresDetailComponent implements OnInit {
  antecedentesFamiliares: IAntecedentesFamiliares | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antecedentesFamiliares }) => (this.antecedentesFamiliares = antecedentesFamiliares));
  }

  previousState(): void {
    window.history.back();
  }
}
