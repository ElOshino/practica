import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PracticaSharedModule } from 'app/shared/shared.module';
import { ConsultaComponent } from './consulta.component';
import { ConsultaDetailComponent } from './consulta-detail.component';
import { ConsultaUpdateComponent } from './consulta-update.component';
import { ConsultaDeleteDialogComponent } from './consulta-delete-dialog.component';
import { consultaRoute } from './consulta.route';

@NgModule({
  imports: [PracticaSharedModule, RouterModule.forChild(consultaRoute)],
  declarations: [ConsultaComponent, ConsultaDetailComponent, ConsultaUpdateComponent, ConsultaDeleteDialogComponent],
  entryComponents: [ConsultaDeleteDialogComponent]
})
export class PracticaConsultaModule {}
