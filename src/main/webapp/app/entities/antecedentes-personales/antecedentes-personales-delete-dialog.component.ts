import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from './antecedentes-personales.service';

@Component({
  templateUrl: './antecedentes-personales-delete-dialog.component.html'
})
export class AntecedentesPersonalesDeleteDialogComponent {
  antecedentesPersonales?: IAntecedentesPersonales;

  constructor(
    protected antecedentesPersonalesService: AntecedentesPersonalesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.antecedentesPersonalesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('antecedentesPersonalesListModification');
      this.activeModal.close();
    });
  }
}
