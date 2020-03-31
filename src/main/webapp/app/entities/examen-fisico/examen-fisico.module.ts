import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PracticaSharedModule } from 'app/shared/shared.module';
import { ExamenFisicoComponent } from './examen-fisico.component';
import { ExamenFisicoDetailComponent } from './examen-fisico-detail.component';
import { ExamenFisicoUpdateComponent } from './examen-fisico-update.component';
import { ExamenFisicoDeleteDialogComponent } from './examen-fisico-delete-dialog.component';
import { examenFisicoRoute } from './examen-fisico.route';

@NgModule({
  imports: [PracticaSharedModule, RouterModule.forChild(examenFisicoRoute)],
  declarations: [ExamenFisicoComponent, ExamenFisicoDetailComponent, ExamenFisicoUpdateComponent, ExamenFisicoDeleteDialogComponent],
  entryComponents: [ExamenFisicoDeleteDialogComponent]
})
export class PracticaExamenFisicoModule {}
