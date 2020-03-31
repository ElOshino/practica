import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExamenFisico } from 'app/shared/model/examen-fisico.model';
import { ExamenFisicoService } from './examen-fisico.service';
import { ExamenFisicoDeleteDialogComponent } from './examen-fisico-delete-dialog.component';

@Component({
  selector: 'jhi-examen-fisico',
  templateUrl: './examen-fisico.component.html'
})
export class ExamenFisicoComponent implements OnInit, OnDestroy {
  examenFisicos?: IExamenFisico[];
  eventSubscriber?: Subscription;

  constructor(
    protected examenFisicoService: ExamenFisicoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.examenFisicoService.query().subscribe((res: HttpResponse<IExamenFisico[]>) => (this.examenFisicos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExamenFisicos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExamenFisico): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExamenFisicos(): void {
    this.eventSubscriber = this.eventManager.subscribe('examenFisicoListModification', () => this.loadAll());
  }

  delete(examenFisico: IExamenFisico): void {
    const modalRef = this.modalService.open(ExamenFisicoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.examenFisico = examenFisico;
  }
}
