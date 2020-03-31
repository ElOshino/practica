import { Moment } from 'moment';
import { IConsulta } from 'app/shared/model/consulta.model';
import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { IMedicamentos } from 'app/shared/model/medicamentos.model';
import { IExamenFisico } from 'app/shared/model/examen-fisico.model';
import { IPaciente } from 'app/shared/model/paciente.model';

export interface IHistoriaUsuario {
  id?: number;
  fecha?: Moment;
  consulta?: IConsulta;
  antecedentesPersonales?: IAntecedentesPersonales;
  antecedentesFamiliares?: IAntecedentesFamiliares;
  medicamento?: IMedicamentos;
  examenFisico?: IExamenFisico;
  paciente?: IPaciente;
}

export class HistoriaUsuario implements IHistoriaUsuario {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public consulta?: IConsulta,
    public antecedentesPersonales?: IAntecedentesPersonales,
    public antecedentesFamiliares?: IAntecedentesFamiliares,
    public medicamento?: IMedicamentos,
    public examenFisico?: IExamenFisico,
    public paciente?: IPaciente
  ) {}
}
