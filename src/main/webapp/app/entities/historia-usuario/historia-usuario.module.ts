import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PracticaSharedModule } from 'app/shared/shared.module';
import { HistoriaUsuarioComponent } from './historia-usuario.component';
import { HistoriaUsuarioDetailComponent } from './historia-usuario-detail.component';
import { HistoriaUsuarioUpdateComponent } from './historia-usuario-update.component';
import { HistoriaUsuarioDeleteDialogComponent } from './historia-usuario-delete-dialog.component';
import { historiaUsuarioRoute } from './historia-usuario.route';

@NgModule({
  imports: [PracticaSharedModule, RouterModule.forChild(historiaUsuarioRoute)],
  declarations: [
    HistoriaUsuarioComponent,
    HistoriaUsuarioDetailComponent,
    HistoriaUsuarioUpdateComponent,
    HistoriaUsuarioDeleteDialogComponent
  ],
  entryComponents: [HistoriaUsuarioDeleteDialogComponent]
})
export class PracticaHistoriaUsuarioModule {}
