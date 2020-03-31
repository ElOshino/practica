import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IExamenFisico, ExamenFisico } from 'app/shared/model/examen-fisico.model';
import { ExamenFisicoService } from './examen-fisico.service';
import { ISignosVitales } from 'app/shared/model/signos-vitales.model';
import { SignosVitalesService } from 'app/entities/signos-vitales/signos-vitales.service';

@Component({
  selector: 'jhi-examen-fisico-update',
  templateUrl: './examen-fisico-update.component.html'
})
export class ExamenFisicoUpdateComponent implements OnInit {
  isSaving = false;
  signosvitales: ISignosVitales[] = [];

  editForm = this.fb.group({
    id: [],
    peso: [],
    talla: [],
    indiceMasaCorporal: [],
    signosVitales: []
  });

  constructor(
    protected examenFisicoService: ExamenFisicoService,
    protected signosVitalesService: SignosVitalesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examenFisico }) => {
      this.updateForm(examenFisico);

      this.signosVitalesService
        .query({ filter: 'historiausuario-is-null' })
        .pipe(
          map((res: HttpResponse<ISignosVitales[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISignosVitales[]) => {
          if (!examenFisico.signosVitales || !examenFisico.signosVitales.id) {
            this.signosvitales = resBody;
          } else {
            this.signosVitalesService
              .find(examenFisico.signosVitales.id)
              .pipe(
                map((subRes: HttpResponse<ISignosVitales>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ISignosVitales[]) => (this.signosvitales = concatRes));
          }
        });
    });
  }

  updateForm(examenFisico: IExamenFisico): void {
    this.editForm.patchValue({
      id: examenFisico.id,
      peso: examenFisico.peso,
      talla: examenFisico.talla,
      indiceMasaCorporal: examenFisico.indiceMasaCorporal,
      signosVitales: examenFisico.signosVitales
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examenFisico = this.createFromForm();
    if (examenFisico.id !== undefined) {
      this.subscribeToSaveResponse(this.examenFisicoService.update(examenFisico));
    } else {
      this.subscribeToSaveResponse(this.examenFisicoService.create(examenFisico));
    }
  }

  private createFromForm(): IExamenFisico {
    return {
      ...new ExamenFisico(),
      id: this.editForm.get(['id'])!.value,
      peso: this.editForm.get(['peso'])!.value,
      talla: this.editForm.get(['talla'])!.value,
      indiceMasaCorporal: this.editForm.get(['indiceMasaCorporal'])!.value,
      signosVitales: this.editForm.get(['signosVitales'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamenFisico>>): void {
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

  trackById(index: number, item: ISignosVitales): any {
    return item.id;
  }
}
