import { Moment } from 'moment';
import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IPaciente {
  id?: number;
  nombre?: string;
  telefono?: string;
  cedula?: number;
  direccion?: string;
  sexo?: string;
  tipoSangre?: string;
  fechaNacimiento?: Moment;
  historiaUsuario?: IHistoriaUsuario;
  doctor?: IDoctor;
}

export class Paciente implements IPaciente {
  constructor(
    public id?: number,
    public nombre?: string,
    public telefono?: string,
    public cedula?: number,
    public direccion?: string,
    public sexo?: string,
    public tipoSangre?: string,
    public fechaNacimiento?: Moment,
    public historiaUsuario?: IHistoriaUsuario,
    public doctor?: IDoctor
  ) {}
}
