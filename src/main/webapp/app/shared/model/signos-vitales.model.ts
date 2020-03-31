import { IExamenFisico } from 'app/shared/model/examen-fisico.model';

export interface ISignosVitales {
  id?: number;
  presionArterial?: number;
  respiracion?: number;
  pulso?: number;
  temperatura?: number;
  historiaUsuario?: IExamenFisico;
}

export class SignosVitales implements ISignosVitales {
  constructor(
    public id?: number,
    public presionArterial?: number,
    public respiracion?: number,
    public pulso?: number,
    public temperatura?: number,
    public historiaUsuario?: IExamenFisico
  ) {}
}
