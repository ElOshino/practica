import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMedicamentos, Medicamentos } from 'app/shared/model/medicamentos.model';
import { MedicamentosService } from './medicamentos.service';

@Component({
  selector: 'jhi-medicamentos-update',
  templateUrl: './medicamentos-update.component.html'
})
export class MedicamentosUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    posologia: [],
    tiempoTomando: []
  });

  constructor(protected medicamentosService: MedicamentosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicamentos }) => {
      this.updateForm(medicamentos);
    });
  }

  updateForm(medicamentos: IMedicamentos): void {
    this.editForm.patchValue({
      id: medicamentos.id,
      nombre: medicamentos.nombre,
      posologia: medicamentos.posologia,
      tiempoTomando: medicamentos.tiempoTomando
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicamentos = this.createFromForm();
    if (medicamentos.id !== undefined) {
      this.subscribeToSaveResponse(this.medicamentosService.update(medicamentos));
    } else {
      this.subscribeToSaveResponse(this.medicamentosService.create(medicamentos));
    }
  }

  private createFromForm(): IMedicamentos {
    return {
      ...new Medicamentos(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      posologia: this.editForm.get(['posologia'])!.value,
      tiempoTomando: this.editForm.get(['tiempoTomando'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicamentos>>): void {
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
