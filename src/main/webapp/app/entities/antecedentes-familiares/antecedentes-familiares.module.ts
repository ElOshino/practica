import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PracticaSharedModule } from 'app/shared/shared.module';
import { AntecedentesFamiliaresComponent } from './antecedentes-familiares.component';
import { AntecedentesFamiliaresDetailComponent } from './antecedentes-familiares-detail.component';
import { AntecedentesFamiliaresUpdateComponent } from './antecedentes-familiares-update.component';
import { AntecedentesFamiliaresDeleteDialogComponent } from './antecedentes-familiares-delete-dialog.component';
import { antecedentesFamiliaresRoute } from './antecedentes-familiares.route';

@NgModule({
  imports: [PracticaSharedModule, RouterModule.forChild(antecedentesFamiliaresRoute)],
  declarations: [
    AntecedentesFamiliaresComponent,
    AntecedentesFamiliaresDetailComponent,
    AntecedentesFamiliaresUpdateComponent,
    AntecedentesFamiliaresDeleteDialogComponent
  ],
  entryComponents: [AntecedentesFamiliaresDeleteDialogComponent]
})
export class PracticaAntecedentesFamiliaresModule {}
