import { ISignosVitales } from 'app/shared/model/signos-vitales.model';
import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

export interface IExamenFisico {
  id?: number;
  peso?: number;
  talla?: number;
  indiceMasaCorporal?: number;
  signosVitales?: ISignosVitales;
  historiaUsuario?: IHistoriaUsuario;
}

export class ExamenFisico implements IExamenFisico {
  constructor(
    public id?: number,
    public peso?: number,
    public talla?: number,
    public indiceMasaCorporal?: number,
    public signosVitales?: ISignosVitales,
    public historiaUsuario?: IHistoriaUsuario
  ) {}
}
