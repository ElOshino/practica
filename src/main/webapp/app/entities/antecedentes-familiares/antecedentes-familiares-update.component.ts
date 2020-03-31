import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAntecedentesFamiliares, AntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from './antecedentes-familiares.service';

@Component({
  selector: 'jhi-antecedentes-familiares-update',
  templateUrl: './antecedentes-familiares-update.component.html'
})
export class AntecedentesFamiliaresUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    madre: [],
    padre: [],
    abueloPaterno: [],
    abueloMaterno: [],
    abuelaPaterna: [],
    abuelaMaterna: []
  });

  constructor(
    protected antecedentesFamiliaresService: AntecedentesFamiliaresService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antecedentesFamiliares }) => {
      this.updateForm(antecedentesFamiliares);
    });
  }

  updateForm(antecedentesFamiliares: IAntecedentesFamiliares): void {
    this.editForm.patchValue({
      id: antecedentesFamiliares.id,
      madre: antecedentesFamiliares.madre,
      padre: antecedentesFamiliares.padre,
      abueloPaterno: antecedentesFamiliares.abueloPaterno,
      abueloMaterno: antecedentesFamiliares.abueloMaterno,
      abuelaPaterna: antecedentesFamiliares.abuelaPaterna,
      abuelaMaterna: antecedentesFamiliares.abuelaMaterna
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const antecedentesFamiliares = this.createFromForm();
    if (antecedentesFamiliares.id !== undefined) {
      this.subscribeToSaveResponse(this.antecedentesFamiliaresService.update(antecedentesFamiliares));
    } else {
      this.subscribeToSaveResponse(this.antecedentesFamiliaresService.create(antecedentesFamiliares));
    }
  }

  private createFromForm(): IAntecedentesFamiliares {
    return {
      ...new AntecedentesFamiliares(),
      id: this.editForm.get(['id'])!.value,
      madre: this.editForm.get(['madre'])!.value,
      padre: this.editForm.get(['padre'])!.value,
      abueloPaterno: this.editForm.get(['abueloPaterno'])!.value,
      abueloMaterno: this.editForm.get(['abueloMaterno'])!.value,
      abuelaPaterna: this.editForm.get(['abuelaPaterna'])!.value,
      abuelaMaterna: this.editForm.get(['abuelaMaterna'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAntecedentesFamiliares>>): void {
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
