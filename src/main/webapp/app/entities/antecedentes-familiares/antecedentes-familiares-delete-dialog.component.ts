import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from './antecedentes-familiares.service';

@Component({
  templateUrl: './antecedentes-familiares-delete-dialog.component.html'
})
export class AntecedentesFamiliaresDeleteDialogComponent {
  antecedentesFamiliares?: IAntecedentesFamiliares;

  constructor(
    protected antecedentesFamiliaresService: AntecedentesFamiliaresService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.antecedentesFamiliaresService.delete(id).subscribe(() => {
      this.eventManager.broadcast('antecedentesFamiliaresListModification');
      this.activeModal.close();
    });
  }
}
