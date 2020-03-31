import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamenFisico } from 'app/shared/model/examen-fisico.model';
import { ExamenFisicoService } from './examen-fisico.service';

@Component({
  templateUrl: './examen-fisico-delete-dialog.component.html'
})
export class ExamenFisicoDeleteDialogComponent {
  examenFisico?: IExamenFisico;

  constructor(
    protected examenFisicoService: ExamenFisicoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examenFisicoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('examenFisicoListModification');
      this.activeModal.close();
    });
  }
}
