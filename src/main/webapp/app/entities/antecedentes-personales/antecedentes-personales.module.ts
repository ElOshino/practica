import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PracticaSharedModule } from 'app/shared/shared.module';
import { AntecedentesPersonalesComponent } from './antecedentes-personales.component';
import { AntecedentesPersonalesDetailComponent } from './antecedentes-personales-detail.component';
import { AntecedentesPersonalesUpdateComponent } from './antecedentes-personales-update.component';
import { AntecedentesPersonalesDeleteDialogComponent } from './antecedentes-personales-delete-dialog.component';
import { antecedentesPersonalesRoute } from './antecedentes-personales.route';

@NgModule({
  imports: [PracticaSharedModule, RouterModule.forChild(antecedentesPersonalesRoute)],
  declarations: [
    AntecedentesPersonalesComponent,
    AntecedentesPersonalesDetailComponent,
    AntecedentesPersonalesUpdateComponent,
    AntecedentesPersonalesDeleteDialogComponent
  ],
  entryComponents: [AntecedentesPersonalesDeleteDialogComponent]
})
export class PracticaAntecedentesPersonalesModule {}
