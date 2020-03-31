import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PracticaSharedModule } from 'app/shared/shared.module';
import { MedicamentosComponent } from './medicamentos.component';
import { MedicamentosDetailComponent } from './medicamentos-detail.component';
import { MedicamentosUpdateComponent } from './medicamentos-update.component';
import { MedicamentosDeleteDialogComponent } from './medicamentos-delete-dialog.component';
import { medicamentosRoute } from './medicamentos.route';

@NgModule({
  imports: [PracticaSharedModule, RouterModule.forChild(medicamentosRoute)],
  declarations: [MedicamentosComponent, MedicamentosDetailComponent, MedicamentosUpdateComponent, MedicamentosDeleteDialogComponent],
  entryComponents: [MedicamentosDeleteDialogComponent]
})
export class PracticaMedicamentosModule {}
