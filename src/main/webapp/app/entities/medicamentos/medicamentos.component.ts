import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicamentos } from 'app/shared/model/medicamentos.model';
import { MedicamentosService } from './medicamentos.service';
import { MedicamentosDeleteDialogComponent } from './medicamentos-delete-dialog.component';

@Component({
  selector: 'jhi-medicamentos',
  templateUrl: './medicamentos.component.html'
})
export class MedicamentosComponent implements OnInit, OnDestroy {
  medicamentos?: IMedicamentos[];
  eventSubscriber?: Subscription;

  constructor(
    protected medicamentosService: MedicamentosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.medicamentosService.query().subscribe((res: HttpResponse<IMedicamentos[]>) => (this.medicamentos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMedicamentos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMedicamentos): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMedicamentos(): void {
    this.eventSubscriber = this.eventManager.subscribe('medicamentosListModification', () => this.loadAll());
  }

  delete(medicamentos: IMedicamentos): void {
    const modalRef = this.modalService.open(MedicamentosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicamentos = medicamentos;
  }
}
