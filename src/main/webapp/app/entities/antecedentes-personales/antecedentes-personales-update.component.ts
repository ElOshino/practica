import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAntecedentesPersonales, AntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from './antecedentes-personales.service';

@Component({
  selector: 'jhi-antecedentes-personales-update',
  templateUrl: './antecedentes-personales-update.component.html'
})
export class AntecedentesPersonalesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cirugias: [],
    traumatismos: [],
    transfusionSanguinea: [],
    alergias: []
  });

  constructor(
    protected antecedentesPersonalesService: AntecedentesPersonalesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antecedentesPersonales }) => {
      this.updateForm(antecedentesPersonales);
    });
  }

  updateForm(antecedentesPersonales: IAntecedentesPersonales): void {
    this.editForm.patchValue({
      id: antecedentesPersonales.id,
      cirugias: antecedentesPersonales.cirugias,
      traumatismos: antecedentesPersonales.traumatismos,
      transfusionSanguinea: antecedentesPersonales.transfusionSanguinea,
      alergias: antecedentesPersonales.alergias
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const antecedentesPersonales = this.createFromForm();
    if (antecedentesPersonales.id !== undefined) {
      this.subscribeToSaveResponse(this.antecedentesPersonalesService.update(antecedentesPersonales));
    } else {
      this.subscribeToSaveResponse(this.antecedentesPersonalesService.create(antecedentesPersonales));
    }
  }

  private createFromForm(): IAntecedentesPersonales {
    return {
      ...new AntecedentesPersonales(),
      id: this.editForm.get(['id'])!.value,
      cirugias: this.editForm.get(['cirugias'])!.value,
      traumatismos: this.editForm.get(['traumatismos'])!.value,
      transfusionSanguinea: this.editForm.get(['transfusionSanguinea'])!.value,
      alergias: this.editForm.get(['alergias'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAntecedentesPersonales>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
