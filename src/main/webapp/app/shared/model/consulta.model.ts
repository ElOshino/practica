import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

export interface IConsulta {
  id?: number;
  sintomas?: string;
  signos?: string;
  tiempoSintomas?: string;
  historiaUsuario?: IHistoriaUsuario;
}

export class Consulta implements IConsulta {
  constructor(
    public id?: number,
    public sintomas?: string,
    public signos?: string,
    public tiempoSintomas?: string,
    public historiaUsuario?: IHistoriaUsuario
  ) {}
}
