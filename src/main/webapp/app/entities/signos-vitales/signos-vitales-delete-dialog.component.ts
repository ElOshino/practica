import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISignosVitales } from 'app/shared/model/signos-vitales.model';
import { SignosVitalesService } from './signos-vitales.service';

@Component({
  templateUrl: './signos-vitales-delete-dialog.component.html'
})
export class SignosVitalesDeleteDialogComponent {
  signosVitales?: ISignosVitales;

  constructor(
    protected signosVitalesService: SignosVitalesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.signosVitalesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('signosVitalesListModification');
      this.activeModal.close();
    });
  }
}
