import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

export interface IAntecedentesPersonales {
  id?: number;
  cirugias?: string;
  traumatismos?: string;
  transfusionSanguinea?: string;
  alergias?: string;
  historiaUsuario?: IHistoriaUsuario;
}

export class AntecedentesPersonales implements IAntecedentesPersonales {
  constructor(
    public id?: number,
    public cirugias?: string,
    public traumatismos?: string,
    public transfusionSanguinea?: string,
    public alergias?: string,
    public historiaUsuario?: IHistoriaUsuario
  ) {}
}
