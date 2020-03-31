import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISignosVitales, SignosVitales } from 'app/shared/model/signos-vitales.model';
import { SignosVitalesService } from './signos-vitales.service';

@Component({
  selector: 'jhi-signos-vitales-update',
  templateUrl: './signos-vitales-update.component.html'
})
export class SignosVitalesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    presionArterial: [],
    respiracion: [],
    pulso: [],
    temperatura: []
  });

  constructor(protected signosVitalesService: SignosVitalesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signosVitales }) => {
      this.updateForm(signosVitales);
    });
  }

  updateForm(signosVitales: ISignosVitales): void {
    this.editForm.patchValue({
      id: signosVitales.id,
      presionArterial: signosVitales.presionArterial,
      respiracion: signosVitales.respiracion,
      pulso: signosVitales.pulso,
      temperatura: signosVitales.temperatura
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const signosVitales = this.createFromForm();
    if (signosVitales.id !== undefined) {
      this.subscribeToSaveResponse(this.signosVitalesService.update(signosVitales));
    } else {
      this.subscribeToSaveResponse(this.signosVitalesService.create(signosVitales));
    }
  }

  private createFromForm(): ISignosVitales {
    return {
      ...new SignosVitales(),
      id: this.editForm.get(['id'])!.value,
      presionArterial: this.editForm.get(['presionArterial'])!.value,
      respiracion: this.editForm.get(['respiracion'])!.value,
      pulso: this.editForm.get(['pulso'])!.value,
      temperatura: this.editForm.get(['temperatura'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISignosVitales>>): void {
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
