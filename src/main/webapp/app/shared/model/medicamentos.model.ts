import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

export interface IMedicamentos {
  id?: number;
  nombre?: string;
  posologia?: string;
  tiempoTomando?: string;
  historiaUsuario?: IHistoriaUsuario;
}

export class Medicamentos implements IMedicamentos {
  constructor(
    public id?: number,
    public nombre?: string,
    public posologia?: string,
    public tiempoTomando?: string,
    public historiaUsuario?: IHistoriaUsuario
  ) {}
}
