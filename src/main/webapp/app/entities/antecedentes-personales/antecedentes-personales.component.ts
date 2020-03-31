import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from './antecedentes-personales.service';
import { AntecedentesPersonalesDeleteDialogComponent } from './antecedentes-personales-delete-dialog.component';

@Component({
  selector: 'jhi-antecedentes-personales',
  templateUrl: './antecedentes-personales.component.html'
})
export class AntecedentesPersonalesComponent implements OnInit, OnDestroy {
  antecedentesPersonales?: IAntecedentesPersonales[];
  eventSubscriber?: Subscription;

  constructor(
    protected antecedentesPersonalesService: AntecedentesPersonalesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.antecedentesPersonalesService
      .query()
      .subscribe((res: HttpResponse<IAntecedentesPersonales[]>) => (this.antecedentesPersonales = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAntecedentesPersonales();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAntecedentesPersonales): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAntecedentesPersonales(): void {
    this.eventSubscriber = this.eventManager.subscribe('antecedentesPersonalesListModification', () => this.loadAll());
  }

  delete(antecedentesPersonales: IAntecedentesPersonales): void {
    const modalRef = this.modalService.open(AntecedentesPersonalesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.antecedentesPersonales = antecedentesPersonales;
  }
}
