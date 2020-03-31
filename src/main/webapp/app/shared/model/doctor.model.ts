import { IPaciente } from 'app/shared/model/paciente.model';

export interface IDoctor {
  id?: number;
  nombre?: string;
  telefono?: string;
  cedula?: number;
  pacientes?: IPaciente[];
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public nombre?: string,
    public telefono?: string,
    public cedula?: number,
    public pacientes?: IPaciente[]
  ) {}
}
