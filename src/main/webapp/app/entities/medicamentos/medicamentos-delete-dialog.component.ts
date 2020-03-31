import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicamentos } from 'app/shared/model/medicamentos.model';
import { MedicamentosService } from './medicamentos.service';

@Component({
  templateUrl: './medicamentos-delete-dialog.component.html'
})
export class MedicamentosDeleteDialogComponent {
  medicamentos?: IMedicamentos;

  constructor(
    protected medicamentosService: MedicamentosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicamentosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('medicamentosListModification');
      this.activeModal.close();
    });
  }
}
