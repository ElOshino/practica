import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IHistoriaUsuario, HistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from './historia-usuario.service';
import { IConsulta } from 'app/shared/model/consulta.model';
import { ConsultaService } from 'app/entities/consulta/consulta.service';
import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from 'app/entities/antecedentes-personales/antecedentes-personales.service';
import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from 'app/entities/antecedentes-familiares/antecedentes-familiares.service';
import { IMedicamentos } from 'app/shared/model/medicamentos.model';
import { MedicamentosService } from 'app/entities/medicamentos/medicamentos.service';
import { IExamenFisico } from 'app/shared/model/examen-fisico.model';
import { ExamenFisicoService } from 'app/entities/examen-fisico/examen-fisico.service';

type SelectableEntity = IConsulta | IAntecedentesPersonales | IAntecedentesFamiliares | IMedicamentos | IExamenFisico;

@Component({
  selector: 'jhi-historia-usuario-update',
  templateUrl: './historia-usuario-update.component.html'
})
export class HistoriaUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  consultas: IConsulta[] = [];
  antecedentespersonales: IAntecedentesPersonales[] = [];
  antecedentesfamiliares: IAntecedentesFamiliares[] = [];
  medicamentos: IMedicamentos[] = [];
  examenfisicos: IExamenFisico[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    fecha: [],
    consulta: [],
    antecedentesPersonales: [],
    antecedentesFamiliares: [],
    medicamento: [],
    examenFisico: []
  });

  constructor(
    protected historiaUsuarioService: HistoriaUsuarioService,
    protected consultaService: ConsultaService,
    protected antecedentesPersonalesService: AntecedentesPersonalesService,
    protected antecedentesFamiliaresService: AntecedentesFamiliaresService,
    protected medicamentosService: MedicamentosService,
    protected examenFisicoService: ExamenFisicoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historiaUsuario }) => {
      this.updateForm(historiaUsuario);

      this.consultaService
        .query({ filter: 'historiausuario-is-null' })
        .pipe(
          map((res: HttpResponse<IConsulta[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IConsulta[]) => {
          if (!historiaUsuario.consulta || !historiaUsuario.consulta.id) {
            this.consultas = resBody;
          } else {
            this.consultaService
              .find(historiaUsuario.consulta.id)
              .pipe(
                map((subRes: HttpResponse<IConsulta>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IConsulta[]) => (this.consultas = concatRes));
          }
        });

      this.antecedentesPersonalesService
        .query({ filter: 'historiausuario-is-null' })
        .pipe(
          map((res: HttpResponse<IAntecedentesPersonales[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAntecedentesPersonales[]) => {
          if (!historiaUsuario.antecedentesPersonales || !historiaUsuario.antecedentesPersonales.id) {
            this.antecedentespersonales = resBody;
          } else {
            this.antecedentesPersonalesService
              .find(historiaUsuario.antecedentesPersonales.id)
              .pipe(
                map((subRes: HttpResponse<IAntecedentesPersonales>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAntecedentesPersonales[]) => (this.antecedentespersonales = concatRes));
          }
        });

      this.antecedentesFamiliaresService
        .query({ filter: 'historiausuario-is-null' })
        .pipe(
          map((res: HttpResponse<IAntecedentesFamiliares[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAntecedentesFamiliares[]) => {
          if (!historiaUsuario.antecedentesFamiliares || !historiaUsuario.antecedentesFamiliares.id) {
            this.antecedentesfamiliares = resBody;
          } else {
            this.antecedentesFamiliaresService
              .find(historiaUsuario.antecedentesFamiliares.id)
              .pipe(
                map((subRes: HttpResponse<IAntecedentesFamiliares>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAntecedentesFamiliares[]) => (this.antecedentesfamiliares = concatRes));
          }
        });

      this.medicamentosService
        .query({ filter: 'historiausuario-is-null' })
        .pipe(
          map((res: HttpResponse<IMedicamentos[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMedicamentos[]) => {
          if (!historiaUsuario.medicamento || !historiaUsuario.medicamento.id) {
            this.medicamentos = resBody;
          } else {
            this.medicamentosService
              .find(historiaUsuario.medicamento.id)
              .pipe(
                map((subRes: HttpResponse<IMedicamentos>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMedicamentos[]) => (this.medicamentos = concatRes));
          }
        });

      this.examenFisicoService
        .query({ filter: 'historiausuario-is-null' })
        .pipe(
          map((res: HttpResponse<IExamenFisico[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IExamenFisico[]) => {
          if (!historiaUsuario.examenFisico || !historiaUsuario.examenFisico.id) {
            this.examenfisicos = resBody;
          } else {
            this.examenFisicoService
              .find(historiaUsuario.examenFisico.id)
              .pipe(
                map((subRes: HttpResponse<IExamenFisico>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IExamenFisico[]) => (this.examenfisicos = concatRes));
          }
        });
    });
  }

  updateForm(historiaUsuario: IHistoriaUsuario): void {
    this.editForm.patchValue({
      id: historiaUsuario.id,
      fecha: historiaUsuario.fecha,
      consulta: historiaUsuario.consulta,
      antecedentesPersonales: historiaUsuario.antecedentesPersonales,
      antecedentesFamiliares: historiaUsuario.antecedentesFamiliares,
      medicamento: historiaUsuario.medicamento,
      examenFisico: historiaUsuario.examenFisico
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historiaUsuario = this.createFromForm();
    if (historiaUsuario.id !== undefined) {
      this.subscribeToSaveResponse(this.historiaUsuarioService.update(historiaUsuario));
    } else {
      this.subscribeToSaveResponse(this.historiaUsuarioService.create(historiaUsuario));
    }
  }

  private createFromForm(): IHistoriaUsuario {
    return {
      ...new HistoriaUsuario(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      consulta: this.editForm.get(['consulta'])!.value,
      antecedentesPersonales: this.editForm.get(['antecedentesPersonales'])!.value,
      antecedentesFamiliares: this.editForm.get(['antecedentesFamiliares'])!.value,
      medicamento: this.editForm.get(['medicamento'])!.value,
      examenFisico: this.editForm.get(['examenFisico'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoriaUsuario>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
