import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

export interface IAntecedentesFamiliares {
  id?: number;
  madre?: string;
  padre?: string;
  abueloPaterno?: string;
  abueloMaterno?: string;
  abuelaPaterna?: string;
  abuelaMaterna?: string;
  historiaUsuario?: IHistoriaUsuario;
}

export class AntecedentesFamiliares implements IAntecedentesFamiliares {
  constructor(
    public id?: number,
    public madre?: string,
    public padre?: string,
    public abueloPaterno?: string,
    public abueloMaterno?: string,
    public abuelaPaterna?: string,
    public abuelaMaterna?: string,
    public historiaUsuario?: IHistoriaUsuario
  ) {}
}
