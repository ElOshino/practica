import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PracticaSharedModule } from 'app/shared/shared.module';
import { SignosVitalesComponent } from './signos-vitales.component';
import { SignosVitalesDetailComponent } from './signos-vitales-detail.component';
import { SignosVitalesUpdateComponent } from './signos-vitales-update.component';
import { SignosVitalesDeleteDialogComponent } from './signos-vitales-delete-dialog.component';
import { signosVitalesRoute } from './signos-vitales.route';

@NgModule({
  imports: [PracticaSharedModule, RouterModule.forChild(signosVitalesRoute)],
  declarations: [SignosVitalesComponent, SignosVitalesDetailComponent, SignosVitalesUpdateComponent, SignosVitalesDeleteDialogComponent],
  entryComponents: [SignosVitalesDeleteDialogComponent]
})
export class PracticaSignosVitalesModule {}
