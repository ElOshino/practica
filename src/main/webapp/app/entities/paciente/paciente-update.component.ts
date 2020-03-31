import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPaciente, Paciente } from 'app/shared/model/paciente.model';
import { PacienteService } from './paciente.service';
import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from 'app/entities/historia-usuario/historia-usuario.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';

type SelectableEntity = IHistoriaUsuario | IDoctor;

@Component({
  selector: 'jhi-paciente-update',
  templateUrl: './paciente-update.component.html'
})
export class PacienteUpdateComponent implements OnInit {
  isSaving = false;
  historiausuarios: IHistoriaUsuario[] = [];
  doctors: IDoctor[] = [];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    telefono: [],
    cedula: [],
    direccion: [],
    sexo: [],
    tipoSangre: [],
    fechaNacimiento: [],
    historiaUsuario: [],
    doctor: []
  });

  constructor(
    protected pacienteService: PacienteService,
    protected historiaUsuarioService: HistoriaUsuarioService,
    protected doctorService: DoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paciente }) => {
      this.updateForm(paciente);

      this.historiaUsuarioService
        .query({ filter: 'paciente-is-null' })
        .pipe(
          map((res: HttpResponse<IHistoriaUsuario[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IHistoriaUsuario[]) => {
          if (!paciente.historiaUsuario || !paciente.historiaUsuario.id) {
            this.historiausuarios = resBody;
          } else {
            this.historiaUsuarioService
              .find(paciente.historiaUsuario.id)
              .pipe(
                map((subRes: HttpResponse<IHistoriaUsuario>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IHistoriaUsuario[]) => (this.historiausuarios = concatRes));
          }
        });

      this.doctorService.query().subscribe((res: HttpResponse<IDoctor[]>) => (this.doctors = res.body || []));
    });
  }

  updateForm(paciente: IPaciente): void {
    this.editForm.patchValue({
      id: paciente.id,
      nombre: paciente.nombre,
      telefono: paciente.telefono,
      cedula: paciente.cedula,
      direccion: paciente.direccion,
      sexo: paciente.sexo,
      tipoSangre: paciente.tipoSangre,
      fechaNacimiento: paciente.fechaNacimiento,
      historiaUsuario: paciente.historiaUsuario,
      doctor: paciente.doctor
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paciente = this.createFromForm();
    if (paciente.id !== undefined) {
      this.subscribeToSaveResponse(this.pacienteService.update(paciente));
    } else {
      this.subscribeToSaveResponse(this.pacienteService.create(paciente));
    }
  }

  private createFromForm(): IPaciente {
    return {
      ...new Paciente(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      cedula: this.editForm.get(['cedula'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      sexo: this.editForm.get(['sexo'])!.value,
      tipoSangre: this.editForm.get(['tipoSangre'])!.value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento'])!.value,
      historiaUsuario: this.editForm.get(['historiaUsuario'])!.value,
      doctor: this.editForm.get(['doctor'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>): void {
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
