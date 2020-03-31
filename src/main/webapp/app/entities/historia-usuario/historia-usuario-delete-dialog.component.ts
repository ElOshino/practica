import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from './historia-usuario.service';

@Component({
  templateUrl: './historia-usuario-delete-dialog.component.html'
})
export class HistoriaUsuarioDeleteDialogComponent {
  historiaUsuario?: IHistoriaUsuario;

  constructor(
    protected historiaUsuarioService: HistoriaUsuarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historiaUsuarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('historiaUsuarioListModification');
      this.activeModal.close();
    });
  }
}
